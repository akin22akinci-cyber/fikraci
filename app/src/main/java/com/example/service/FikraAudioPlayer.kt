package com.example.service

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.media.AudioManager
import android.media.ToneGenerator
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.PI
import kotlin.math.sin

data class AudioPlaybackState(
    val isSpeaking: Boolean = false,
    val currentJokeId: Long? = null,
    val currentJokeTitle: String? = null,
    val speechRate: Float = 1.0f,
    val isTtsReady: Boolean = false
)

class FikraAudioPlayer(private val context: Context) : TextToSpeech.OnInitListener {

    private val TAG = "FikraAudioPlayer"
    private var textToSpeech: TextToSpeech? = null
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _playbackState = MutableStateFlow(AudioPlaybackState())
    val playbackState: StateFlow<AudioPlaybackState> = _playbackState.asStateFlow()

    init {
        textToSpeech = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale.forLanguageTag("tr-TR"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Fallback to default locale if Turkish voice data is missing
                textToSpeech?.language = Locale.getDefault()
            }
            textToSpeech?.setSpeechRate(_playbackState.value.speechRate)
            textToSpeech?.setPitch(1.05f) // Friendly, slightly upbeat comedy pitch

            textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    scope.launch {
                        _playbackState.value = _playbackState.value.copy(isSpeaking = true)
                    }
                }

                override fun onDone(utteranceId: String?) {
                    scope.launch {
                        _playbackState.value = _playbackState.value.copy(
                            isSpeaking = false,
                            currentJokeId = null,
                            currentJokeTitle = null
                        )
                    }
                }

                override fun onError(utteranceId: String?) {
                    scope.launch {
                        _playbackState.value = _playbackState.value.copy(
                            isSpeaking = false,
                            currentJokeId = null,
                            currentJokeTitle = null
                        )
                    }
                }
            })

            _playbackState.value = _playbackState.value.copy(isTtsReady = true)
        } else {
            Log.e(TAG, "TextToSpeech init failed with status: $status")
        }
    }

    fun playJoke(jokeId: Long, title: String, content: String) {
        if (_playbackState.value.isSpeaking && _playbackState.value.currentJokeId == jokeId) {
            stop()
            return
        }

        stop()
        val speechText = "$title. $content"
        _playbackState.value = _playbackState.value.copy(
            isSpeaking = true,
            currentJokeId = jokeId,
            currentJokeTitle = title
        )
        val params = android.os.Bundle()
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "joke_$jokeId")
        textToSpeech?.speak(speechText, TextToSpeech.QUEUE_FLUSH, params, "joke_$jokeId")
    }

    fun stop() {
        textToSpeech?.stop()
        _playbackState.value = _playbackState.value.copy(
            isSpeaking = false,
            currentJokeId = null,
            currentJokeTitle = null
        )
    }

    fun setSpeechRate(rate: Float) {
        textToSpeech?.setSpeechRate(rate)
        _playbackState.value = _playbackState.value.copy(speechRate = rate)
    }

    /**
     * Plays the legendary comedy punchline rimshot ("Ba-dum tss!") using AudioTrack synthesized waveforms.
     */
    fun playPunchlineRimshot() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                // Synthesize a quick two-beat drum and cymbal sizzle (Ba-Dum-Tsss)
                val sampleRate = 22050
                val totalDurationMs = 700
                val numSamples = (sampleRate * (totalDurationMs / 1000.0)).toInt()
                val buffer = ShortArray(numSamples)

                for (i in 0 until numSamples) {
                    val time = i.toDouble() / sampleRate
                    var sample = 0.0

                    // First beat (Ba) around 0 - 120ms (frequency ~160Hz)
                    if (time in 0.0..0.12) {
                        val env = (1.0 - time / 0.12)
                        sample += sin(2.0 * PI * 160.0 * time) * env * 0.7
                    }
                    // Second beat (Dum) around 150 - 300ms (frequency ~130Hz)
                    if (time in 0.15..0.30) {
                        val env = (1.0 - (time - 0.15) / 0.15)
                        sample += sin(2.0 * PI * 130.0 * (time - 0.15)) * env * 0.9
                    }
                    // Cymbal Crash (Tssss) from 300ms onward: noise burst with decaying envelope
                    if (time >= 0.30) {
                        val tCymbal = time - 0.30
                        val env = Math.exp(-tCymbal * 8.0)
                        val noise = (Math.random() * 2.0 - 1.0)
                        sample += noise * env * 0.6
                    }

                    buffer[i] = (sample.coerceIn(-1.0, 1.0) * Short.MAX_VALUE).toInt().toShort()
                }

                val track = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(buffer.size * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                track.write(buffer, 0, buffer.size)
                track.play()
            } catch (e: Exception) {
                // Fallback to simple tone generator
                try {
                    val tone = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
                    tone.startTone(ToneGenerator.TONE_PROP_BEEP, 300)
                } catch (_: Exception) {}
            }
        }
    }

    /**
     * Plays a cheerful standup comedy applause / laughter intro jingle.
     */
    fun playComedyJingle() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val sampleRate = 22050
                val totalDurationMs = 1200
                val numSamples = (sampleRate * (totalDurationMs / 1000.0)).toInt()
                val buffer = ShortArray(numSamples)

                // Melody notes: C5 (523Hz), E5 (659Hz), G5 (784Hz), C6 (1046Hz) arpeggio fanfare!
                val notes = listOf(523.25, 659.25, 783.99, 1046.50)
                val noteDuration = 0.22

                for (i in 0 until numSamples) {
                    val time = i.toDouble() / sampleRate
                    val noteIndex = (time / noteDuration).toInt().coerceIn(0, notes.size - 1)
                    val noteFreq = notes[noteIndex]
                    val noteTime = time - (noteIndex * noteDuration)
                    val env = Math.exp(-noteTime * 4.0)

                    val sample = (sin(2.0 * PI * noteFreq * time) + 0.3 * sin(2.0 * PI * noteFreq * 2.0 * time)) * env * 0.7
                    buffer[i] = (sample.coerceIn(-1.0, 1.0) * Short.MAX_VALUE).toInt().toShort()
                }

                val track = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(buffer.size * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                track.write(buffer, 0, buffer.size)
                track.play()
            } catch (e: Exception) {
                Log.e(TAG, "Error playing comedy jingle", e)
            }
        }
    }

    fun release() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
    }
}

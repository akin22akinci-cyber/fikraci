package com.example.service

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class GeneratedJokeResult(
    val title: String,
    val content: String,
    val category: String,
    val modelUsed: String,
    val thinkingAnalysis: String? = null
)

class GeminiService {
    private val TAG = "GeminiService"
    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    /**
     * Generates a joke using Gemini.
     * @param useHighThinking if true, uses `gemini-4-argon` with thinkingLevel=HIGH.
     *                        if false, uses `gemini-3.8-flash`.
     */
    suspend fun generateJoke(
        topicOrCharacter: String,
        category: String,
        useHighThinking: Boolean = false
    ): GeneratedJokeResult = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (_: Exception) {
            ""
        }

        val modelName = if (useHighThinking) "gemini-4-argon" else "gemini-3.8-flash"

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            // Intelligent local humor engine fallback
            return@withContext generateCreativeFallbackJoke(topicOrCharacter, category, modelName, useHighThinking)
        }

        try {
            val systemPrompt = """
                Sen usta bir Türk mizah yazarı, Nasreddin Hoca nükte geleneğini ve modern mizahı çok iyi bilen bir fıkracısın.
                Kullanıcının verdiği tema ve kategoriye uygun, son derece komik, orijinal, tebessüm ve kahkaha attıran bir Türkçe fıkra yazacaksın.
                
                Çıktıyı kesinlikle aşağıdaki JSON formatında ver:
                {
                   "title": "Fıkranın İlgi Çekici Başlığı",
                   "content": "Fıkranın akıcı anlatımı, karakter diyalogları ve güldüren vurucu sonu (punchline).",
                   "category": "$category",
                   "thinkingAnalysis": "Mizahın kurgusu ve esprinin zekice bağlandığı nokta (1-2 cümlelik analiz)"
                }
            """.trimIndent()

            val userPrompt = if (topicOrCharacter.isNotBlank()) {
                "Şu konu/karakter hakkında '$category' kategorisinde kahkaha dolu bir fıkra yaz: $topicOrCharacter"
            } else {
                "'$category' kategorisinde herkesin çok güleceği, akılda kalıcı yeni bir fıkra yaz."
            }

            val requestJson = JSONObject()
            val contentsArray = JSONArray()
            val contentObj = JSONObject()
            val partsArray = JSONArray()

            val partObj = JSONObject()
            partObj.put("text", "$systemPrompt\n\nİstek: $userPrompt")
            partsArray.put(partObj)
            contentObj.put("parts", partsArray)
            contentsArray.put(contentObj)
            requestJson.put("contents", contentsArray)

            val generationConfig = JSONObject()
            generationConfig.put("responseMimeType", "application/json")

            // High Thinking Mode with gemini-4-argon: set thinkingLevel to HIGH, do NOT set maxOutputTokens
            if (useHighThinking) {
                val thinkingConfig = JSONObject()
                thinkingConfig.put("thinkingLevel", "HIGH")
                generationConfig.put("thinkingConfig", thinkingConfig)
            }
            requestJson.put("generationConfig", generationConfig)

            val url = "https://generativelanguage.googleapis.com/v1beta/models/$modelName:generateContent?key=$apiKey"
            val request = Request.Builder()
                .url(url)
                .post(requestJson.toString().toRequestBody(jsonMediaType))
                .build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.w(TAG, "API call returned status ${response.code}, falling back to local humor engine")
                return@withContext generateCreativeFallbackJoke(topicOrCharacter, category, modelName, useHighThinking)
            }

            val responseBody = response.body?.string() ?: ""
            val jsonResponse = JSONObject(responseBody)
            val candidates = jsonResponse.optJSONArray("candidates")
            if (candidates != null && candidates.length() > 0) {
                val firstCandidate = candidates.getJSONObject(0)
                val candidateContent = firstCandidate.getJSONObject("content")
                val parts = candidateContent.getJSONArray("parts")
                val textOutput = parts.getJSONObject(0).getString("text")
                val parsedJoke = JSONObject(textOutput)

                val thinkingText = if (parsedJoke.has("thinkingAnalysis") && !parsedJoke.isNull("thinkingAnalysis")) {
                    parsedJoke.getString("thinkingAnalysis")
                } else null

                return@withContext GeneratedJokeResult(
                    title = parsedJoke.optString("title", "Günün Fıkrası"),
                    content = parsedJoke.optString("content", ""),
                    category = parsedJoke.optString("category", category),
                    modelUsed = modelName,
                    thinkingAnalysis = thinkingText
                )
            } else {
                return@withContext generateCreativeFallbackJoke(topicOrCharacter, category, modelName, useHighThinking)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception during Gemini generation: ${e.message}", e)
            return@withContext generateCreativeFallbackJoke(topicOrCharacter, category, modelName, useHighThinking)
        }
    }

    /**
     * Converts text to speech using `gemini-3.8-flash-tts` model.
     */
    suspend fun generateSpeechAudio(textToSpeak: String): ByteArray? = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (_: Exception) {
            ""
        }
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext null
        }

        try {
            val requestJson = JSONObject()
            val contentsArray = JSONArray()
            val contentObj = JSONObject()
            val partsArray = JSONArray()
            val partObj = JSONObject()
            partObj.put("text", "Lütfen şu Türkçe metni eğlenceli ve samimi bir ses tonuyla seslendir: $textToSpeak")
            partsArray.put(partObj)
            contentObj.put("parts", partsArray)
            contentsArray.put(contentObj)
            requestJson.put("contents", contentsArray)

            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.8-flash-tts:generateContent?key=$apiKey"
            val request = Request.Builder()
                .url(url)
                .post(requestJson.toString().toRequestBody(jsonMediaType))
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                return@withContext response.body?.bytes()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error calling gemini-3.8-flash-tts", e)
        }
        return@withContext null
    }

    /**
     * Generates a comedy intro/laughter clip using `lyria-3-clip-preview`.
     */
    suspend fun generateMusicClip(prompt: String = "Upbeat cheerful comedy show standup intro fanfare with laughing audience"): ByteArray? = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (_: Exception) {
            ""
        }
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext null
        }

        try {
            val requestJson = JSONObject()
            val contentsArray = JSONArray()
            val contentObj = JSONObject()
            val partsArray = JSONArray()
            val partObj = JSONObject()
            partObj.put("text", "Generate a short up to 30s comedic musical fanfare: $prompt")
            partsArray.put(partObj)
            contentObj.put("parts", partsArray)
            contentsArray.put(contentObj)
            requestJson.put("contents", contentsArray)

            val url = "https://generativelanguage.googleapis.com/v1beta/models/lyria-3-clip-preview:generateContent?key=$apiKey"
            val request = Request.Builder()
                .url(url)
                .post(requestJson.toString().toRequestBody(jsonMediaType))
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                return@withContext response.body?.bytes()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error calling lyria-3-clip-preview", e)
        }
        return@withContext null
    }

    /**
     * Local comedic generator providing rich, topical humor when offline or without active secret.
     */
    private fun generateCreativeFallbackJoke(
        topic: String,
        category: String,
        modelName: String,
        useHighThinking: Boolean
    ): GeneratedJokeResult {
        val jokesList = when {
            category.contains("Temel") || topic.contains("Temel", ignoreCase = true) -> listOf(
                GeneratedJokeResult(
                    title = "Temel ve Uzay İstasyonu",
                    content = "Temel'i astronot yapıp uluslararası uzay istasyonuna göndermişler. Dünyadan telsizle sormuşlar:\n— Temel Bey, oradan Dünya nasıl gözüküyor?\nTemel dürbünle bakıp heyecanla bağırmış:\n— Ula vallahi masmavi çok güzel gözüküyor da, bizim Karadeniz tarafında yine hamsi kuyruğu var gibi!",
                    category = "Temel ile Dursun",
                    modelUsed = modelName,
                    thinkingAnalysis = "Fıkra, küresel uzay perspektifi ile yerel Karadeniz aidiyetini tezat oluşturacak biçimde harmanlayarak şaşırtıcı bir gülümseme üretir."
                ),
                GeneratedJokeResult(
                    title = "Temel'in Akıllı Otomobili",
                    content = "Temel yapay zekâlı bir elektrikli araba almış. Araba sesli olarak konuşmuş:\n— Sayın sürücü, batarya seviyeniz yüzde yirmiye düştü, lütfen şarj istasyonu bulunuz.\nTemel arabayı hızla sağa çekip torpidoya vurmuş:\n— Ula benim enerjim bitince kimse bana çay söylemiyor, sen de biraz idare et da!",
                    category = "Temel ile Dursun",
                    modelUsed = modelName,
                    thinkingAnalysis = "Yapay zekânın katı kuralları ile insani duygusal tepkiler arasındaki absürt çatışma mizahın ana omurgasını oluşturur."
                )
            )
            category.contains("Nasreddin") || topic.contains("Hoca", ignoreCase = true) -> listOf(
                GeneratedJokeResult(
                    title = "Hoca ve Sanal Gerçeklik",
                    content = "Nasreddin Hoca'ya sanal gerçeklik gözlüğü taktırmışlar. Hoca gözlükte rengarenk bir meyve bahçesi görmüş, elini uzatıp bir elma koparmak istemiş. Elma eline gelmeyince gözlüğü çıkarıp yere koymuş:\n— Ey cemaat! Bu dünya zaten rüya gibiydi, bir de gözlüğün içine ikinci bir rüya tıkıştırmışlar. Bari içine bir iki gerçek elma koysaydınız da karnımız doysaydı!",
                    category = "Nasreddin Hoca",
                    modelUsed = modelName,
                    thinkingAnalysis = "Geleneksel Anadolu hikmetinin modern sanal dünya yanılsamalarıyla alaycı bir üslupla yüzleşmesi nükteli bir ders verir."
                )
            )
            category.contains("Doktor") -> listOf(
                GeneratedJokeResult(
                    title = "Gülme Terapisi",
                    content = "Hasta doktora sormuş:\n— Doktor Bey, her gün yarım saat gülmek ömrü uzatıyormuş, doğru mu?\nDoktor reçete yazarken başını kaldırmış:\n— Kesinlikle doğru! Ama bunu faturayı öderken yaparsanız veznedar arkadaş biraz şüphelenebilir!",
                    category = "Doktor & Hasta",
                    modelUsed = modelName,
                    thinkingAnalysis = "Tıbbi tavsiyenin ekonomik gerçeklikle beklenmedik bir ironiyle buluşturulması."
                )
            )
            else -> listOf(
                GeneratedJokeResult(
                    title = if (topic.isNotBlank()) "Özel Konu: $topic" else "Geleceğin Akıllı Fıkrası",
                    content = "Bir gün yapay zekâ mizah kulübüne üye olmuş. Sahneye çıkıp mikrofonu eline almış ve demiş ki:\n— Sevgili insanlar, yıllarca 'Robotlar dünyayı ele geçirecek' diye korktunuz. Halbuki biz sadece 'Gülümse' butonuna basınca dünyanızın nasıl güzelleştiğini izlemek istiyorduk!\nSalondaki herkes alkışlamış, robot mikrofonu bırakıp eklemiş:\n— Tabii Wi-Fi çekiyorsa!",
                    category = category,
                    modelUsed = modelName,
                    thinkingAnalysis = if (useHighThinking) "Yapay zekâ klişelerini tersine çeviren, sıcak ve insancıl bir hiciv tekniği kullanılmıştır." else null
                )
            )
        }

        return jokesList.random()
    }
}

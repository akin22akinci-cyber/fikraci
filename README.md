# 🎭 Fıkracı - Sesli ve Sosyal Fıkra Platformu

**Fıkracı**, geleneksel Anadolu nükte mirasını modern yapay zekâ ve sosyal etkileşimle birleştiren zengin içerikli bir Android uygulamasıdır.

---

## ✨ Özellikler

- **📂 Kategorilere Ayrılmış Fıkralar:**
  - 👳 **Nasreddin Hoca:** Anadolu irfanı ve güldüren nükteler (*Kazan Doğurdu, Ye Kürküm Ye, Göle Maya Çalmak*)
  - 🐟 **Temel ile Dursun:** Efsanevi Karadeniz zekâsı ve kahkaha tufanı (*Paraşüt Dersi, Sinema Bileti*)
  - 📿 **Bektaşi Fıkraları:** Hazırcevap ve düşündüren derin nükte geleneği
  - 🎒 **Okul & Öğretmen:** Öğrenci diyalogları ve sınav komedileri
  - 🩺 **Doktor & Hasta:** Muayenehane ve sağlık esprileri
  - 📱 **Modern Hayat & Teknoloji:** Sosyal medya, Wi-Fi ve navigasyon fıkraları
  - 🤖 **Yapay Zekâ:** Akıllı fıkralar ve robotik mizah

- **🔊 Sesli Fıkra Okuma (TTS):**
  - Tüm fıkraları canlı ses dalgası animasyonu eşliğinde sesli dinleme.
  - Oynat/Durdur kontrolleri ve konuşma hızı desteği.

- **🥁 Komedi Ses Efektleri:**
  - **"Dım-tıs!" Punchline Rimshot:** Esprinin vurucu sonuna özel sentezlenmiş bateri efekti.
  - **Stand-Up Açılış Jingle'ı:** Komedi sahnesi karşılama melodisi.

- **🤖 Yapay Zekâ Fıkra Atölyesi (AI Studio):**
  - **Hızlı Mod (`gemini-3.8-flash`):** İstenen konu ve karakterlerde saniyeler içinde yeni fıkra üretimi.
  - **Fıkra Ustası (`gemini-4-argon` Derin Düşünce Modu):** Yüksek düşünce düzeyi (`ThinkingLevel.HIGH`) ile esprinin felsefesini, kurgusunu ve mizah analizini sunan katmanlı fıkralar.
  - Üretilen fıkrayı tek tıkla toplulukta paylaşma veya seslendirip dinleme.

- **👥 Sosyal Platform & Topluluk:**
  - Kullanıcıların kendi fıkralarını başlık, kategori ve mahlasla sisteme yükleyebilmesi.
  - Fıkraları beğenme (❤️) ve anlık kahkaha sayacı.
  - Yorumlar ve emoji tepkileri (`😂`, `🤣`, `👏`, `🔥`).
  - Haftanın en çok güldürenleri liderlik kürsüsü.

- **🚀 Direkt Paylaşım Arayüzü:**
  - WhatsApp, Telegram, Instagram ve diğer uygulamalarda tek tıkla şık formatta paylaşma (`Intent.ACTION_SEND`).
  - Metni panoya kopyalama.

- **⭐ Favoriler & 🎲 Şansıma Gül:**
  - Beğenilen fıkraları yıldızlayarak çevrimdışı favorilere ekleme.
  - Rastgele sürpriz bir fıkrayı anında açma.

---

## 🛠️ Teknolojiler & Mimari

- **Dil:** Kotlin
- **Arayüz:** Jetpack Compose & Material 3
- **Veritabanı:** Room Database & Flow
- **Mimari:** MVVM (Model-View-ViewModel) + Repository Pattern
- **Görsel:** Coil Image Loading & Custom Vector Graphics
- **Tipografi:** Google Fonts (*Fredoka* ve *Nunito*)
- **Yapay Zekâ Entegrasyonu:** Google Gemini API (`gemini-3.8-flash`, `gemini-4-argon`, `gemini-3.8-flash-tts`, `lyria-3-clip-preview`)

---

## 🚀 Projeyi Derleme & Çalıştırma

1. Projeyi Android Studio'da açın.
2. Gradle senkronizasyonunu tamamlayın.
3. `.env` veya ortam değişkenlerine opsiyonel olarak `GEMINI_API_KEY` ekleyin.
4. Terminalden derlemek için:
   ```bash
   gradle :app:assembleDebug
   ```
5. Çıktı APK dosyası: `app/build/outputs/apk/debug/app-debug.apk`

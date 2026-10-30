package com.example.data.seed

import com.example.data.model.JokeEntity

object SeedJokes {
    val initialJokes = listOf(
        // Nasreddin Hoca
        JokeEntity(
            title = "Kazan Doğurdu",
            content = "Nasreddin Hoca bir gün komşusundan büyük bir kazan ödünç alır. İşi bitince kazanın içine küçük bir tencere koyup komşusuna geri götürür.\n\nKomşusu şaşkınlıkla sorar:\n— Hoca Efendi, bu tencere neyin nesi?\nHoca tebessüm eder:\n— Müjde komşu, senin kazan bizdeyken doğurdu!\n\nKomşu bu işe çok sevinir, tencereyi de kazanı da hemen içeri alır. Aradan birkaç ay geçer, Hoca kazanı yine ödünç ister. Komşu seve seve verir.\n\nFakat haftalar geçmesine rağmen Hoca kazanı getirmez. Komşu meraklanıp Hoca'nın kapısını çalar:\n— Yahu Hoca, bizim kazan nerede kaldı?\nHoca derin bir iç çeker, başını sallar:\n— Ah komşum sorma, başın sağ olsun... Senin kazan vefat etti!\n\nKomşu öfkeyle bağırır:\n— Hoca Efendi aklını mı yitirdin sen? Hiç koca demir kazan ölür mü!\nHoca sakalını sıvazlayıp taşı gediğine koyar:\n— Yahu komşu, kazanın doğurduğuna inanıyorsun da, öldüğüne niye inanmıyorsun?",
            category = "Nasreddin Hoca",
            authorName = "Akşehirli Dost",
            likesCount = 342,
            isFavorite = true,
            commentsCount = 3,
            commentsJson = """[{"id":"1","author":"Mehmet","text":"Klasiklerin klasiği, her dinlediğimde gülüyorum 😂","timestamp":1730280000000},{"id":"2","author":"Ayşe","text":"Hoca'nın mantığına hayran kalmamak elde değil!","timestamp":1730283600000},{"id":"3","author":"Caner","text":"Doğurunca iyi, ölünce kötü haha!","timestamp":1730287200000}]""",
            isAiGenerated = false
        ),
        JokeEntity(
            title = "Ye Kürküm Ye",
            content = "Nasreddin Hoca'yı bir gün önemli bir ziyafete davet ederler. Hoca günlük, eski püskü cübbesiyle davete gider. Ne karşılayan olur, ne de buyur eden. Kimse Hoca'ya dönüp bakmaz bile.\n\nHoca durumu anlar, sessizce evine döner. Sandıktan en gösterişli, samur kürkünü çıkarıp giyer ve ziyafet salonuna geri döner.\n\nBu kez kapıda eğilerek selamlarlar, en baş köşeye oturturlar. Sofranın en lezzetli yemekleri Hoca'nın önüne yığılır.\n\nHoca çorba kasesine yaklaşır, kürkünün kolunu çorbanın içine daldırıp şöyle der:\n— Ye kürküm ye! Ye kürküm ye! İtibar bana değil, sanaymış meğer!",
            category = "Nasreddin Hoca",
            authorName = "Gülmece Kulübü",
            likesCount = 289,
            isFavorite = false,
            commentsCount = 2,
            commentsJson = """[{"id":"1","author":"Burak","text":"Günümüz toplumunu 600 yıl önceden özetlemiş koca Hoca.","timestamp":1730290000000},{"id":"2","author":"Zeynep","text":"Muazzam bir nükte!","timestamp":1730291000000}]""",
            isAiGenerated = false
        ),
        JokeEntity(
            title = "Parayı Veren Düdüğü Çalar",
            content = "Nasreddin Hoca pazara giderken köyün çocukları etrafını sarmış. Kimi elma, kimi düdük, kimi şeker istemiş. Çocuklardan sadece biri elindeki birkaç kuruşu Hoca'ya uzatarak:\n— Hoca amca, bana pazardan bir düdük alır mısın?\nHoca akşamüstü pazardan döner. Bütün çocuklar heyecanla başına toplanır. Hoca cebinden bir tek düdük çıkarıp parayı veren çocuğa uzatır.\n\nDiğer çocuklar bağırışır:\n— Ya bizimkiler nerede Hoca?\nHoca tebessümle yanıtlar:\n— Parayı veren düdüğü çalar evlatlar!",
            category = "Nasreddin Hoca",
            authorName = "Mizahsever",
            likesCount = 195,
            isFavorite = false,
            commentsCount = 1,
            commentsJson = """[{"id":"1","author":"Selim","text":"Ekonominin ilk kuralı!","timestamp":1730292000000}]""",
            isAiGenerated = false
        ),
        JokeEntity(
            title = "Göle Maya Çalmak",
            content = "Nasreddin Hoca bir gün Akşehir Gölü'nün kıyısına oturmuş, çömleğindeki yoğurdu kaşık kaşık göle döküyormuş.\n\nYoldan geçen köylülerden biri şaşkınlıkla seslenmiş:\n— Hayırdır Hoca Efendi, ne yapıyorsun öyle?\nHoca ciddi bir yüzle cevap vermiş:\n— Göle maya çalıyorum komşu.\nKöylü gülmüş:\n— İlahi Hoca, koca göl hiç maya tutar mı?\nHoca kaşığı daldırıp tebessümle fısıldamış:\n— Ya tutarsa!",
            category = "Nasreddin Hoca",
            authorName = "Anadolu Nefesi",
            likesCount = 412,
            isFavorite = true,
            commentsCount = 2,
            commentsJson = """[{"id":"1","author":"Emre","text":"Umudun ve pozitifliğin simgesi fıkra :)","timestamp":1730293000000}]""",
            isAiGenerated = false
        ),

        // Temel ile Dursun
        JokeEntity(
            title = "Paraşüt Dersi",
            content = "Temel askerliğini paraşütçü olarak yapmaktadır. Komutan uçaktan atlamadan önce kuralları hatırlatır:\n— Askerler! Uçaktan atlayınca önce sağdaki birinci ipi çekeceksiniz. Paraşüt açılmazsa soldaki yedek ipi çekeceksiniz. Yere indiğinizde de sizi bir kamyon bekliyor olacak, birliğe döneceksiniz!\n\nUçak havalanır, Temel kendini boşluğa bırakır. Sağdaki ipi çeker... tık yok! Soldaki yedek ipi çeker... yine tık yok!\n\nTemel havada süzülürken kafasını sallar ve mırıldanır:\n— Ula bu komutan da amma yalancı çıktı ha! Şimdi yere indiğimde kamyon da orada yoksa gör bak ben ona ne yapıyorum!",
            category = "Temel ile Dursun",
            authorName = "Karadeniz Rüzgarı",
            likesCount = 512,
            isFavorite = true,
            commentsCount = 4,
            commentsJson = """[{"id":"1","author":"Onur","text":"Temel'in rahatlığı şaka mı ahaha!","timestamp":1730294000000},{"id":"2","author":"Fadime","text":"Kamyon yoksa fena bozulur Temel :)","timestamp":1730295000000}]""",
            isAiGenerated = false
        ),
        JokeEntity(
            title = "Sinema Bileti",
            content = "Temel sinemaya gitmiş. Gişedeki kıza yaklaşmış:\n— Hanımefendi, bir bilet verir misiniz?\nBileti almış, içeri girmiş. Aradan iki dakika geçmiş, Temel yine gelmiş:\n— Bir bilet daha verir misiniz?\nBileti almış girmiş. Beş dakika sonra Temel ter içinde tekrar gişeye koşmuş:\n— Bir bilet daha lütfen!\n\nGişeci kız merakla sormuş:\n— Beyefendi, demin iki tane aldınız zaten, niye sürekli bilet alıyorsunuz?\nTemel öfkeyle elini sallamış:\n— Sorma hemşerim! İçeride kapının önünde dikilen bir deli var, ben içeri her girdiğimde biletimi yırtıp atıyor!",
            category = "Temel ile Dursun",
            authorName = "Trabzonlu61",
            likesCount = 428,
            isFavorite = true,
            commentsCount = 3,
            commentsJson = """[{"id":"1","author":"Murat","text":"Yer göstericiyi deli sanması efsane!","timestamp":1730296000000}]""",
            isAiGenerated = false
        ),
        JokeEntity(
            title = "Uçak ve Dursun",
            content = "Temel ile Dursun ilk defa uçağa binerler. Uçak havada seyrederken birden kaptan anons yapar:\n— Değerli yolcularımız, uçağımızın sağ motoru arızalanmıştır. Endişe etmeyiniz, tek motorla uçuşumuza devam ediyoruz fakat inişimiz bir saat gecikecektir.\n\nBiraz sonra bir anons daha:\n— Sayın yolcular, maalesef sol motorda da sorun çıktı, inişimiz üç saat gecikecek.\n\nTemel arkasına yaslanır, Dursun'a dönüp söylenir:\n— Ula Dursun, dua et şu üçüncü motor da bozulmasın, yoksa bu geceyi kesin havada geçireceğiz!",
            category = "Temel ile Dursun",
            authorName = "Laz Uşağı",
            likesCount = 377,
            isFavorite = false,
            commentsCount = 2,
            commentsJson = """[{"id":"1","author":"Kerem","text":"Geceyi havada geçirmek haha harika kafa!","timestamp":1730297000000}]""",
            isAiGenerated = false
        ),
        JokeEntity(
            title = "Hamsi ve Kedi",
            content = "Dursun kahvehanede Temel'e hava atıyormuş:\n— Temel, benim evde öyle akıllı bir kedi var ki, balık getirdiğimde önce temizliyor, sonra tavasını yapıp masaya servis ediyor!\n\nTemel hiç istifini bozmadan çayından bir yudum almış:\n— Biliyorum Dursun, biliyorum.\nDursun şaşırmış:\n— Nereden biliyorsun yahu?\nTemel cevap vermiş:\n— Benim kedi söyledi!",
            category = "Temel ile Dursun",
            authorName = "Rizeli Gülüm",
            likesCount = 265,
            isFavorite = false,
            commentsCount = 1,
            commentsJson = """[{"id":"1","author":"Selin","text":"Karadeniz usulü karşı atak!","timestamp":1730298000000}]""",
            isAiGenerated = false
        ),

        // Bektaşi Fıkraları
        JokeEntity(
            title = "Şarap ve Cennet",
            content = "Vaizin biri camide hararetle anlatıyormuş:\n— Ey cemaat! Bu dünyada şarap haramdır, içenler cehennemde yanacaktır! Fakat cennete gidenlere Kevser şarabı ırmaklar gibi akacaktır!\n\nÖn safta oturan Bektaşi dayanamayıp söze karışmış:\n— Yahu vaiz efendi! Biz bu dünyada bir kadeh içtik diye ateşte yakacaksın, öbür tarafta koca ırmağı vaat ediyorsun. Bu iş bağ bozumuna mı benziyor, yoksa toptancılık mı yapıyorsun?",
            category = "Bektaşi",
            authorName = "Erenler Meclisi",
            likesCount = 310,
            isFavorite = false,
            commentsCount = 2,
            commentsJson = """[{"id":"1","author":"Hüseyin","text":"Bektaşi nükte gücü bambaşka bir zekâ.","timestamp":1730299000000}]""",
            isAiGenerated = false
        ),
        JokeEntity(
            title = "Yağmur Duası",
            content = "Kurak bir yaz günü köylüler yağmur duasına çıkmaya karar vermişler. Yolda Bektaşi'ye rastlamışlar:\n— Erenler, biz yağmur duasına gidiyoruz, sen de gel!\nBektaşi gökyüzüne bakıp:\n— Ben gelmeyeyim, siz gidin, demiş.\nKöylüler ısrar etmiş, dua etmişler ama tek damla düşmemiş. Dönerken Bektaşi'ye sitem etmişler:\n— Sen gelmedin, duamız da kabul olmadı!\nBektaşi gülümseyip cevabı yapıştırmış:\n— Ben sizin samimiyetinize inanmadım ki! Madem yağmur yağacağına inanıyordunuz, hani hiçbirinizin elinde şemsiye yoktu?",
            category = "Bektaşi",
            authorName = "Derviş Nüktesi",
            likesCount = 450,
            isFavorite = true,
            commentsCount = 3,
            commentsJson = """[{"id":"1","author":"Tolga","text":"İnanç ve eylem arasındaki farkı bundan iyi anlatan çıkmaz.","timestamp":1730300000000}]""",
            isAiGenerated = false
        ),

        // Okul & Öğrenci
        JokeEntity(
            title = "Tarih Sınavı",
            content = "Öğretmen sınıfta Ali'yi tahtaya kaldırıp sorar:\n— Söyle bakalım Ali, İstanbul'u kim fethetti?\nAli korkudan titreyerek cevap verir:\n— Valla öğretmenim ben fethetmedim! Dün hastaydım, zaten okula da yeni geldim!\n\nÖğretmen sinirlenip durumu okul müdürüne şikayet eder:\n— Müdür Bey, çocuğa 'İstanbul'u kim fethetti' diyorum, 'ben fethetmedim' diyor!\nMüdür Bey bıyıklarını sıvazlayıp öğretmeni teselli eder:\n— Hocam büyütmeyin bu kadar... Belki de gerçekten o fethetmemiştir, üstüne gitmeyin çocuğun!",
            category = "Okul & Öğretmen",
            authorName = "Teneffüs Zili",
            likesCount = 360,
            isFavorite = false,
            commentsCount = 2,
            commentsJson = """[{"id":"1","author":"Gökhan","text":"Müdür bey noktayı koymuş hahaha!","timestamp":1730301000000}]""",
            isAiGenerated = false
        ),
        JokeEntity(
            title = "Coğrafya ve Dünya",
            content = "Öğretmen sınıfa koca bir yerküre getirip küçük Mehmet'e sormuş:\n— Mehmet, gel buraya. Parmağını şu küre üzerinde Amerika'nın üzerine koy.\nMehmet parmağını koymuş.\nÖğretmen bu kez bütün sınıfa dönmüş:\n— Evet çocuklar, Amerika'yı kim keşfetti?\nBütün sınıf bir ağızdan bağırmış:\n— Mehmet!",
            category = "Okul & Öğretmen",
            authorName = "Sınıf Başkanı",
            likesCount = 275,
            isFavorite = false,
            commentsCount = 1,
            commentsJson = """[{"id":"1","author":"Berna","text":"Çocuk aklı ne tatlı :)","timestamp":1730302000000}]""",
            isAiGenerated = false
        ),

        // Doktor & Hasta
        JokeEntity(
            title = "Gözlük ve Kitap",
            content = "Yaşlı bir amca göz doktoruna gitmiş:\n— Doktor evladım, yaşlandım artık, bana iyi bir okuma gözlüğü yaz.\nDoktor muayene edip en uygun gözlüğü vermiş:\n— Amca al bakalım, bununla rahat rahat okursun.\n\nAmca gözlüğü takmış, önüne koyulan gazeteye bakmış bakmış, kafasını sallamış:\n— Yok doktorcum, ben yine okuyamıyorum!\nDoktor şaşırmış, başka cam denemiş, yine olmamış. Sonunda sormuş:\n— Amca sen daha önce okuma yazma biliyor muydun?\nAmca sitemle cevap vermiş:\n— Yahu evladım okuma yazma bilsem sana niye geleyim! Ben bu gözlük beni okutur diye geldim!",
            category = "Doktor & Hasta",
            authorName = "Şifalı Kahkaha",
            likesCount = 380,
            isFavorite = true,
            commentsCount = 2,
            commentsJson = """[{"id":"1","author":"Fatma","text":"Optik teknoloji henüz o seviyede değil amcacım haha","timestamp":1730303000000}]""",
            isAiGenerated = false
        ),
        JokeEntity(
            title = "Hafıza Kaybı",
            content = "Hasta nefes nefese doktorun odasına girer:\n— Doktor Bey, çok büyük bir sorunum var! Her şeyi iki saniye içinde unutuyorum!\nDoktor koltuğuna yaslanıp dikkatle sorar:\n— Ne zamandan beri var bu şikayetiniz?\nHasta boş gözlerle doktora bakar:\n— Hangi şikayetim?",
            category = "Doktor & Hasta",
            authorName = "Reçetesiz Tebessüm",
            likesCount = 290,
            isFavorite = false,
            commentsCount = 1,
            commentsJson = """[{"id":"1","author":"Yasin","text":"İki saniye dediyse iki saniye!","timestamp":1730304000000}]""",
            isAiGenerated = false
        ),

        // Modern Hayat & Teknoloji
        JokeEntity(
            title = "Wi-Fi Şifresi",
            content = "Köyün kahvehanesine ilk defa Wi-Fi bağlanmış. Bütün gençler ellerinde telefonlarla kahveci İdris Efendi'nin etrafını sarmış:\n— İdris Amca, Wi-Fi şifresi ne?\nİdris Amca bezgin bir sesle bağırmış:\n— Önce bir çay için!\n\nGençler hemen ikişer çay söyleyip afiyetle içmişler. Masaları boşaltıp yine gelmişler:\n— İdris Amca çayları içtik, şifreyi söyler misin artık?\nİdris Amca tebessümle duvardaki tabelayı işaret etmiş:\n— Dedim ya evladım: 'öncebirçayiçin', hepsi küçük harf, bitişik!",
            category = "Modern Hayat",
            authorName = "Dijital Nasreddin",
            likesCount = 620,
            isFavorite = true,
            commentsCount = 5,
            commentsJson = """[{"id":"1","author":"Arda","text":"Pazarlama dehası İdris Amca!","timestamp":1730305000000},{"id":"2","author":"Seda","text":"Bizim kafede de aynısını yapmalılar haha","timestamp":1730306000000}]""",
            isAiGenerated = false
        ),
        JokeEntity(
            title = "Navigasyon ve Temel",
            content = "Temel arabasına en son model sesli navigasyon cihazı taktırmış. Yola çıkmış, navigasyon kadın sesiyle konuşuyor:\n— 200 metre sonra sağa dönünüz.\nTemel sağa dönmüş.\n— Şimdi sola dönünüz.\nTemel sola dönmüş.\n— Düz devam ediniz.\nTemel düz gitmiş.\n\nBir saat sonra navigasyon anons yapmış:\n— Rota yeniden hesaplanıyor... Hedefe ulaştınız.\nTemel etrafına bakmış; deniz kıyısında, Trabzon limanındalar. Temel gaza basmadan önce dikiz aynasındaki cihaza dönüp bağırmış:\n— Ula bacım beni buraya getirdin de, şimdi hamsilerle mi toplantı yapacağım ben?!",
            category = "Modern Hayat",
            authorName = "Oto Komedi",
            likesCount = 410,
            isFavorite = false,
            commentsCount = 2,
            commentsJson = """[{"id":"1","author":"Cem","text":"Navigasyonla kavga eden tek milletiz :)","timestamp":1730307000000}]""",
            isAiGenerated = false
        ),

        // Yapay Zekâ Fıkraları
        JokeEntity(
            title = "Yapay Zekâ ve Temel",
            content = "Bilim insanları Karadeniz'e gidip dünyanın en gelişmiş süper yapay zekâsını Temel'e tanıtmışlar:\n— Temel Bey, bu yapay zekâ her soruya kusursuz cevap verir, saniyede milyarlarca ihtimali düşünür. Buyrun, istediğinizi sorun!\n\nTemel bıyıklarını burmuş, ekrana doğru eğilip sormuş:\n— Ula kutu, ben şimdi ne düşünüyorum?\nYapay zekânın ekranında 5 saniye 'Düşünülüyor...' yazısı dönmüş, işlemcileri alev almış, fanları feryat etmiş ve ekranda şu yazı belirmiş:\n— 'Hata 404: Karadenizli mantığı algoritmaya sığmamaktadır!'",
            category = "Yapay Zekâ",
            authorName = "Gemini Usta",
            likesCount = 740,
            isFavorite = true,
            commentsCount = 6,
            commentsJson = """[{"id":"1","author":"Alperen","text":"Yapay zekanın tek çaresiz kaldığı yer Karadeniz haha!","timestamp":1730308000000},{"id":"2","author":"Hilal","text":"Derin düşünce modu bile çözememiş :)","timestamp":1730309000000}]""",
            isAiGenerated = true,
            aiModelUsed = "gemini-4-argon",
            thinkingAnalysis = "Fıkra, yapay zekânın rasyonel ve olasılıksal veri analitiği ile Temel'in mantık sınırlarını aşan sezgisel mizacını karşılaştırarak absürt bir nükte kurgular."
        ),
        JokeEntity(
            title = "Nasreddin Hoca ve Akıllı Saat",
            content = "Nasreddin Hoca'ya torunu doğum gününde son model bir akıllı saat hediye etmiş. Hoca koluna takmış, pazara gitmiş.\n\nSaat birden titreşip seslenmiş:\n— Çok uzun süre oturdunuz, lütfen ayağa kalkın ve 500 adım yürüyün!\nHoca durmuş, saate bakmış:\n— Yahu evlat, ben zaten eşekten yeni indim, belim tutmuyor!\nSaat bu kez uyarmış:\n— Kalp atışınız yükseldi, lütfen derin nefes alın!\nHoca saati kolundan çıkarıp eşeğinin kulağına bağlamış:\n— Al bunu arkadaş, ikiniz de aynı dilden konuşuyorsunuz, bana akıl vereceğine git eşeği terbiye et!",
            category = "Yapay Zekâ",
            authorName = "Fıkracı AI",
            likesCount = 590,
            isFavorite = false,
            commentsCount = 3,
            commentsJson = """[{"id":"1","author":"Enes","text":"Eşeğe akıllı saat takmak fikri muhteşem!","timestamp":1730310000000}]""",
            isAiGenerated = true,
            aiModelUsed = "gemini-3.8-flash"
        )
    )
}

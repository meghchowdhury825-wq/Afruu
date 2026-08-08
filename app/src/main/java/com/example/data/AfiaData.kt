package com.example.data

data class DetailItem(
    val title: String,
    val subtitle: String,
    val description: String,
    val tag: String = ""
)

data class WorldCategory(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconEmoji: String,
    val items: List<DetailItem>
)

data class GalleryItem(
    val id: Int,
    val title: String,
    val caption: String,
    val category: String,
    val colorHex: Long,
    val secondaryColorHex: Long,
    val imageUrl: String? = null
)

data class SocialLink(
    val platformName: String,
    val username: String,
    val url: String,
    val iconEmoji: String
)

data class SecretLetter(
    val id: String,
    val senderName: String,
    val content: String,
    val timestamp: String
)

object AfiaData {
    const val PERSON_NAME = "আফিয়া আফরিন"
    const val CHITHI_URL = "https://chithi.me/Mst_Afiya143"
    const val CHITHI_LABEL = "নাম প্রকাশ না করেও চিঠি পাঠানো যাবে"

    val HERO_QUOTE = "কিছু গল্প বলা হয়,\nকিছু গল্প শুধু অনুভব করা যায়।"
    val HERO_BUTTON_PRIMARY = "আমার জগৎ ঘুরে দেখুন"
    val HERO_BUTTON_SECONDARY = "💌 বিশেষ বক্স"

    val ABOUT_TITLE = "আমার সম্পর্কে"
    val ABOUT_TEXT = "আফিয়া আফরিন—নিজের ছোট্ট জগৎ, পছন্দ, অনুভূতি আর সুন্দর মুহূর্তগুলো নিয়ে তৈরি তার এই ব্যক্তিগত জায়গা।"

    val ABOUT_TRAITS = listOf(
        Pair("ব্যক্তিত্ব", "রুচিশীল ও প্রাণবন্ত"),
        Pair("পছন্দ", "বই • গান • ছবি • সুন্দর মুহূর্ত"),
        Pair("প্রিয় উক্তি", "সব সৌন্দর্য চোখে দেখা যায় না,\nকিছু সৌন্দর্য অনুভব করতে হয়।")
    )

    val HER_WORLD_TITLE = "আমার জগৎ"
    val HER_WORLD_SUBTITLE = "যে ছোট ছোট জিনিসগুলো আমার পৃথিবীকে সুন্দর করে।"

    val WORLD_CATEGORIES = listOf(
        WorldCategory(
            id = "books",
            title = "বই",
            subtitle = "যে গল্পগুলো ভালো লাগে",
            iconEmoji = "📚",
            items = listOf(
                DetailItem("শেষের কবিতা", "রবীন্দ্রনাথ ঠাকুর", "প্রেম ও জীবনের এক চিরন্তন আখ্যান। অমিত ও লাবণ্যের কাব্যিক সম্পর্কের গভীর অনুভূতি।", "উপন্যাস"),
                DetailItem("শুভ্র সিরিজ", "হুমায়ূন আহমেদ", "এক নিষ্পাপ তরুণের জগত আর তার চারপাশের অদ্ভুত সুন্দর গল্প।", "জনপ্রিয়"),
                DetailItem("একাত্তরের দিনগুলি", "জাহানারা ইমাম", "মুক্তিযুদ্ধের হৃদয়স্পর্শী ডায়েরি ও একজন মায়ের অমর স্মৃতি।", "ইতিহাস"),
                DetailItem("জোছনা ও জননীর গল্প", "হুমায়ূন আহমেদ", "একাত্তরের যুদ্ধ ও বাঙালি জীবনের বিস্তৃত রূপcanvas।", "মহাকাব্যিক"),
                DetailItem("মেঘের ওপর বাড়ি", "বুদ্ধদেব বসু", "স্মৃতি ও ভালোবাসার স্নিগ্ধ ছায়া নিয়ে রচিত মনস্তাত্ত্বিক ক্যানভাস।", "ধ্রুপদী")
            )
        ),
        WorldCategory(
            id = "music",
            title = "গান",
            subtitle = "যে সুরগুলো মনে থেকে যায়",
            iconEmoji = "🎵",
            items = listOf(
                DetailItem("তুমি আসবে বলেই", "নচিকেতা চক্রবর্তী", "অপেক্ষার মিষ্টতা ও গভীর ভালোবাসার সুর।", "জীবনমুখী"),
                DetailItem("কোথাও কেউ নেই", "আর্টসেল", "মেলাঙ্কোলিক সুর আর আত্মমগ্ন পংক্তিমালা।", "রক / মেলোডি"),
                DetailItem("আজ এই বৃষ্টির কান্না দেখে", "নিয়াজ মোহাম্মদ চৌধুরী", "বৃষ্টিভেজা অলস দুপুরের ক্লাসিক আবেদন।", "ধ্রুপদী"),
                DetailItem("যে রাতে মোর দুয়ারগুলি", "রবীন্দ্রসংগীত", "নির্জন রাতের আত্মোপলব্ধি ও শান্ত সুরের দোলা।", "রবীন্দ্রসংগীত"),
                DetailItem("একলা ঘর", "ফসিলস", "একাকীত্বের গভীরে লুকিয়ে থাকা এক অন্য রূপ।", "আধুনিক")
            )
        ),
        WorldCategory(
            id = "movies",
            title = "সিনেমা",
            subtitle = "পর্দার প্রিয় গল্পগুলো",
            iconEmoji = "🎬",
            items = listOf(
                DetailItem("মনপুরা", "গিয়াস উদ্দিন সেলু", "পরীর দ্বীপের চিরন্তন রূপকথা ও অমর প্রেমের গল্প।", "চলচ্চিত্র"),
                DetailItem("শ্রাবণ মেঘের দিন", "হুমায়ূন আহমেদ", "গ্রামবাংলার সৌন্দর্য, সুর আর বিরহের অনন্য সৃষ্টি।", "ক্লাসিক"),
                DetailItem("আয়নাবাজি", "অমিতাভ রেজা চৌধুরী", "মানুষের রূপ পরিবর্তন ও জীবনের নাটকীয় রহস্য।", "থ্রিলার"),
                DetailItem("পথের পাঁচালী", "সত্যজিৎ রায়", "অপু আর দুর্গার শৈশব ও বাঙালির জীবনের চিরন্তন দলিল।", "বিশ্বমানের"),
                DetailItem("অটোগ্রাফ", "সৃজিত মুখার্জি", "স্মৃতি, সাফল্য আর সম্পর্কের জটিল বাঁক।", "ড্রামা")
            )
        ),
        WorldCategory(
            id = "pleasures",
            title = "ছোট ছোট ভালো লাগা",
            subtitle = "যে মুহূর্তগুলো হাসি এনে দেয়",
            iconEmoji = "☕",
            items = listOf(
                DetailItem("বৃষ্টি ও চা", "এক কাপ গরম চা ও ঝুম বৃষ্টি", "জানালার পাশে বসে বৃষ্টির ফোঁটা গোনার আনমনে ভালো লাগা।", "স্মৃতি"),
                DetailItem("শরতের কাশফুল", "শুভ্র কাশবন ও নীল আকাশ", "শরতের মৃদুমন্দ বাতাসে সাদা মেঘের খেলা দেখা।", "প্রকৃতি"),
                DetailItem("পুরোনো বইয়ের সুবাস", "কাগজের সুবাস ও নীরবতা", "নতুন বা পুরোনো বইয়ের পাতা উল্টানোর রোমাঞ্চ।", "অনুভূতি"),
                DetailItem("বিকেলের স্নিগ্ধ রোদ", "গোধূলির সোনাঝরা আলো", "দিন শেষের মিষ্টি আলোয় একটু শান্ত হয়ে বসা।", "শান্তি"),
                DetailItem("রাতের প্রিয় সুর", "একাকী নির্জনতায় গান", "হাতে ফোন বা হেডফোনে প্রিয় গানের মেলোডি।", "আবহ")
            )
        )
    )

    val GALLERY_TITLE = "মুহূর্তগুলো"
    val GALLERY_SUBTITLE = "কিছু মুহূর্ত ছবিতে থেকে যায়,\nকিছু মুহূর্ত মনে।"

    val GALLERY_ITEMS = listOf(
        GalleryItem(
            id = 1,
            title = "আফিয়া আফরিন",
            caption = "স্নিগ্ধতা ও অনাবিল সৌন্দর্যে ঘেরা অনন্য এক সুন্দর মুহূর্ত।",
            category = "আফিয়া",
            colorHex = 0xFF4A1E2C,
            secondaryColorHex = 0xFF8B3A52,
            imageUrl = "https://ibb.co.com/GvwpJxZV"
        ),
        GalleryItem(
            id = 2,
            title = "বৃষ্টিভেজা জানালার কাঁচ",
            caption = "একলা বিকেলে চায়ের কাপে মেঘের প্রতিচ্ছবি।",
            category = "স্মৃতি",
            colorHex = 0xFF3D1C2A,
            secondaryColorHex = 0xFF7A2E4B
        ),
        GalleryItem(
            id = 3,
            title = "শরতের শুভ্র কাশফুল",
            caption = "নীল আকাশের নিচে নরম হাওয়ায় দোল খাওয়া মুহূর্ত।",
            category = "প্রকৃতি",
            colorHex = 0xFF2F3E46,
            secondaryColorHex = 0xFF52796F
        ),
        GalleryItem(
            id = 4,
            title = "খোলা বই ও শুকনো গোলাপ",
            caption = "প্রিয় লাইনের ভাঁজে রেখে দেওয়া পুরোনো স্মৃতি।",
            category = "অনুভূতি",
            colorHex = 0xFF4A1E2C,
            secondaryColorHex = 0xFF8B3A52
        ),
        GalleryItem(
            id = 5,
            title = "নদীর ঘাটে গোধূলির রোদ",
            caption = "শান্ত জলের ওপর ছড়িয়ে পড়া সোনালী আভা।",
            category = "গোধূলি",
            colorHex = 0xFF5C2D16,
            secondaryColorHex = 0xFFB05D2B
        ),
        GalleryItem(
            id = 6,
            title = "নির্জনতায় প্রিয় সুর",
            caption = "ছোট্ট প্রদীপের মিষ্টি আলোয় হারিয়ে যাওয়া মন।",
            category = "শান্তি",
            colorHex = 0xFF231A38,
            secondaryColorHex = 0xFF4B3B6B
        ),
        GalleryItem(
            id = 7,
            title = "এক কাপ ধোঁয়া ওঠা চা",
            caption = "গল্পের শুরুতে কিংবা শেষে, নীরব সঙ্গী।",
            category = "ভালো লাগা",
            colorHex = 0xFF442416,
            secondaryColorHex = 0xFF824C33
        )
    )

    val SPECIAL_BOX_TITLE = "💌 বিশেষ বক্স"
    val SPECIAL_BOX_MAIN_TEXT = "কিছু কথা বলা যায় না,\nলিখে দেওয়া যায়।"
    val SPECIAL_BOX_SUB_TEXT = "মনের কথাগুলো লিখে ফেলুন।\nহয়তো সে পড়বে।"
    val SPECIAL_BOX_BUTTON = "✉️ আফিয়াকে একটি চিঠি লিখুন"

    val SOCIAL_TITLE = "আফিয়ার সাথে যুক্ত থাকুন"
    val SOCIAL_SUBTITLE = "সামাজিক যোগাযোগমাধ্যমে আফিয়াকে খুঁজে নিন।"

    val SOCIAL_LINKS = listOf(
        SocialLink("ফেসবুক", "@afiyanishi5143", "https://facebook.com/afiyanishi5143", "📘"),
        SocialLink("ইনস্টাগ্রাম", "@afru_bbz_143", "https://instagram.com/afru_bbz_143", "📷"),
        SocialLink("টিকটক", "@afru_bbz_143", "https://tiktok.com/@afru_bbz_143", "🎵"),
        SocialLink("আফিয়াকে চিঠি লিখুন", "chithi.me/Mst_Afiya143", "https://chithi.me/Mst_Afiya143", "💌")
    )

    val FEATURED_BIG_QUOTE = "সব সৌন্দর্য চোখে দেখা যায় না,\nকিছু সৌন্দর্য অনুভব করতে হয়।"

    val PROFILE_IMAGE_URL = "https://i.ibb.co.com/4Zytfmbj/Messenger-creation-3-DE4-D9-FF-EC83-4683-8813-5-B60942-A03-AF.jpg"

    fun formatDirectImageUrl(url: String): String {
        if (url.isBlank()) return url
        val clean = url.trim()
        if (clean.contains("ibb.co") && !clean.contains("i.ibb.co")) {
            return if (clean.contains("ibb.co.com")) {
                clean.replace("ibb.co.com", "i.ibb.co.com").trimEnd('/') + "/image.jpg"
            } else {
                clean.replace("ibb.co", "i.ibb.co").trimEnd('/') + "/image.jpg"
            }
        }
        return clean
    }

    val FOOTER_NAME = "আফিয়া আফরিন"
    val FOOTER_SUBTITLE = "তার ছোট্ট ডিজিটাল জগৎ।"
    val FOOTER_LOVE = "ভালোবাসা দিয়ে তৈরি ♡"

    val APP_LINK = "https://ais-pre-2h64llc6vtfdntkinmkzrn-385344039239.asia-southeast1.run.app"
    val APP_SHARE_MESSAGE = "আফিয়া আফরিনের ডিজিটাল জগৎ অ্যাপ লিঙ্ক: https://ais-pre-2h64llc6vtfdntkinmkzrn-385344039239.asia-southeast1.run.app"
}

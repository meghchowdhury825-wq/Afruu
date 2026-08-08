package com.example.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object BengaliUtils {
    /**
     * Converts standard Latin digits to Bengali digits.
     * e.g., 123 -> ১২৩
     */
    fun toBengaliNumerals(number: Int): String {
        return number.toString().map { char ->
            when (char) {
                '0' -> '০'
                '1' -> '১'
                '2' -> '২'
                '3' -> '৩'
                '4' -> '৪'
                '5' -> '৫'
                '6' -> '৬'
                '7' -> '৭'
                '8' -> '৮'
                '9' -> '৯'
                else -> char
            }
        }.joinToString("")
    }

    /**
     * Converts a string with standard digits to Bengali digits.
     */
    fun convertDigitsToBengali(text: String): String {
        return text.map { char ->
            when (char) {
                '0' -> '০'
                '1' -> '১'
                '2' -> '২'
                '3' -> '৩'
                '4' -> '৪'
                '5' -> '৫'
                '6' -> '৬'
                '7' -> '৭'
                '8' -> '৮'
                '9' -> '৯'
                else -> char
            }
        }.joinToString("")
    }

    /**
     * Opens a web link safely in the browser.
     */
    fun openWebLink(context: Context, url: String) {
        try {
            var formattedUrl = url.trim()
            if (!formattedUrl.startsWith("http://") && !formattedUrl.startsWith("https://")) {
                formattedUrl = "https://$formattedUrl"
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "লিংকটি ব্রাউজারে খোলা সম্ভব হয়নি", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Copies text to Android clipboard.
     */
    fun copyToClipboard(context: Context, label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = android.content.ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "কপি করা হয়েছে: $text", Toast.LENGTH_SHORT).show()
    }

    /**
     * Shares text using system share chooser.
     */
    fun shareText(context: Context, text: String, title: String = "শেয়ার করুন") {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            context.startActivity(Intent.createChooser(intent, title))
        } catch (e: Exception) {
            Toast.makeText(context, "শেয়ার করা সম্ভব হয়নি", Toast.LENGTH_SHORT).show()
        }
    }
}

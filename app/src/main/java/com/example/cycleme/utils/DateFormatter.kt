package com.example.cycleme.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object DateFormatter {
    fun String.withDateFormat(timeZoneId: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(this) ?: return ""

        val outputFormat = SimpleDateFormat("dd MMM yyyy | HH:mm", Locale.US)
        outputFormat.timeZone = TimeZone.getTimeZone(timeZoneId)
        return outputFormat.format(date)
    }
}
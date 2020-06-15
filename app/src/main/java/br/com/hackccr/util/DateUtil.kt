package br.com.hackccr.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun getFormatedDateTime(dateStr: String?, strReadFormat: String?, strWriteFormat: String?): String? {
        var formattedDate = dateStr
        val readFormat: DateFormat = SimpleDateFormat(strReadFormat, Locale("pt-BR"))
        val writeFormat: DateFormat = SimpleDateFormat(strWriteFormat, Locale("pt-BR"))
        var date: Date? = null
        try {
            date = readFormat.parse(dateStr)
        } catch (e: ParseException) {
        }
        if (date != null) {
            formattedDate = writeFormat.format(date)
        }
        return formattedDate
    }
}
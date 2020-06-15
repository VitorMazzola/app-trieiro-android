package br.com.hackccr.util

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import br.com.hackccr.R

fun String.addSpan(context: Context) : SpannableStringBuilder {
    val str = SpannableStringBuilder(this)
    str.setSpan(android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, str.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    str.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.gray)), 0, str.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

    return str
}

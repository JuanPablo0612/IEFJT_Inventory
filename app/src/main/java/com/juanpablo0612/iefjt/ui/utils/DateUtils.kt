package com.juanpablo0612.iefjt.ui.utils

import android.content.Context
import android.text.format.DateFormat.getLongDateFormat
import android.text.format.DateFormat.getTimeFormat
import java.util.Date

fun Context.getDateText(date: Date): String {
    val longDateFormat = getLongDateFormat(this).format(date)
    val timeFormat = getTimeFormat(this).format(date)
    return "$longDateFormat $timeFormat"
}
package com.example.j2ttblogsapp.extension

import kotlin.math.pow
import kotlin.math.roundToInt

fun Int.getCompactedNumber(number: Int): String {

    val c = arrayOf("K", "L", "Cr")

    val size: Int = java.lang.String.valueOf(number).length
    if (size in 4..5) {
        val value = 10.0.pow(1.0).toInt()
        val d = (number / 1000.0 * value).roundToInt().toDouble() / value
        return ((number / 1000.0 * value).roundToInt().toDouble() / value).toString() + c[0]
    } else if (size in 6..7) {
        val value = 10.0.pow(1.0).toInt()
        return ((number / 100000.0 * value).roundToInt().toDouble() / value).toString() + c[1]
    } else if (size >= 8) {
        val value = 10.0.pow(1.0).toInt()
        return ((number / 10000000.0 * value).roundToInt()
            .toDouble() / value).toString() + c[2]
    } else {
        return number.toString() + "";
    }
}
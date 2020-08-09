package com.example.j2ttblogsapp.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.pow
import kotlin.math.roundToInt

fun Int.getCompactedNumber(): String {

    val c = arrayOf("K", "L", "Cr")

    val size: Int = java.lang.String.valueOf(this).length
    when {
        size in 4..5 -> {
            val value = 10.0.pow(1.0).toInt()
            val d = (this / 1000.0 * value).roundToInt().toDouble() / value
            return ((this / 1000.0 * value).roundToInt().toDouble() / value).toString() + c[0]
        }
        size in 6..7 -> {
            val value = 10.0.pow(1.0).toInt()
            return ((this / 100000.0 * value).roundToInt().toDouble() / value).toString() + c[1]
        }
        size >= 8 -> {
            val value = 10.0.pow(1.0).toInt()
            return ((this / 10000000.0 * value).roundToInt()
                .toDouble() / value).toString() + c[2]
        }
        else -> {
            return this.toString() + "";
        }
    }
}

fun String.convertTime(): String {
    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    var convertedTime = ""

    val pastDate: Date?
    val currentDate = Date()
    try {
        pastDate = input.parse(this)
        val dateDiff: Long = currentDate.time - pastDate.time
        val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
        val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
        val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
        val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)

        when {
            second < 60 -> {
                convertedTime = "$second Seconds"
            }
            minute < 60 -> {
                convertedTime = "$minute Minutes"
            }
            hour < 24 -> {
                convertedTime = "$hour Hours"
            }
            day >= 7 -> {
                convertedTime = when {
                    day > 360 -> {
                        "${day / 360} Years"
                    }
                    day > 30 -> {
                        "${day / 30} Months"
                    }
                    else -> {
                        "${day / 7} Week"
                    }
                }
            }
            day < 7 -> {
                convertedTime = "$day Days"
            }
        }

    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return convertedTime
}
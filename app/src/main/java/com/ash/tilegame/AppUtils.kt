package com.ash.tilegame

import android.content.Context
import android.util.DisplayMetrics
import android.widget.Toast
import java.lang.NumberFormatException

internal fun calculateNoOfColumns(context: Context, columnWidthDp: Float): Int {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
    return (screenWidthDp / columnWidthDp + 0.5).toInt()
}

fun String.stringToInt(): Int {
    return try {
        this.toInt()
    } catch (e: NumberFormatException) {
        -1
    }
}

fun showShortToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}


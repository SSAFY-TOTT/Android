package com.ssafy.tott.ui.util

import android.content.Intent
import android.os.Build

fun <T> Intent.getParcelable(tag: String, clazz: Class<T>) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(tag, clazz)
    } else {
        getParcelableExtra(tag)
    }
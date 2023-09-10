package com.ssafy.tott.ui.util

import android.os.Build
import android.os.Bundle

inline fun <reified T : Any> Bundle.parcelableArray(tag: String, clazz: Class<T>): Array<T>? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableArray(tag, clazz)
    } else {
        val array = getParcelableArray(tag) as Array<*>
        if (array.isArrayOf<T>()) {
            array.map { it as T }.toTypedArray()
        } else {
            null
        }
    }
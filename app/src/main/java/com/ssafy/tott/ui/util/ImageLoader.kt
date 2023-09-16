package com.ssafy.tott.ui.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ssafy.tott.R

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun ImageView.loadRandomImage() {
    Glide.with(this)
        .load(images.random())
        .into(this)
}

private val images =
    listOf(R.drawable.house1, R.drawable.house2, R.drawable.house3, R.drawable.house4)
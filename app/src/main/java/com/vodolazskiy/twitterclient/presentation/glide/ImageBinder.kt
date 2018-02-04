package com.vodolazskiy.twitterclient.presentation.glide

import android.widget.ImageView

interface ImageBinder {

    fun loadImage(view: ImageView, url: String?)
}
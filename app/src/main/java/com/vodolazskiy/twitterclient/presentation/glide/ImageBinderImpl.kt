package com.vodolazskiy.twitterclient.presentation.glide

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vodolazskiy.twitterclient.R
import javax.inject.Inject

class ImageBinderImpl @Inject constructor() : ImageBinder {

    override fun loadImage(view: ImageView, url: String?) {
        val ro = RequestOptions()
                .placeholder(R.drawable.tw_grey_background)
                .error(R.drawable.tw_grey_background)

        Glide.with(view.context)
                .load(url)
                .apply(ro)
                .into(view)
    }
}
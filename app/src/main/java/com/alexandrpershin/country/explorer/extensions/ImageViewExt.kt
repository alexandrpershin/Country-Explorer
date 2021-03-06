package com.alexandrpershin.country.explorer.extensions

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

/**
 * Load images with *.svg extension
 * */

fun ImageView.loadSvgFromUrl(
    url: String,
    @DrawableRes placeholderRes: Int? = null,
    width: Int? = null,
    height: Int? = null
) {
    val requestBuilder = GlideToVectorYou.init().with(context).requestBuilder

    requestBuilder.apply {
        placeholderRes?.let {
            placeholder(placeholderRes)
        }

        if (width != null && height != null)
            override(width, height)

        load(Uri.parse(url))
        into(this@loadSvgFromUrl)
    }

}
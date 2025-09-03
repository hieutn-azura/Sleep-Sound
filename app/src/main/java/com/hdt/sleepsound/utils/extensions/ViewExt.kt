package com.hdt.sleepsound.utils.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import java.io.File

fun ImageView.loadImage(@DrawableRes icon: Int) {
    Glide.with(this)
        .load(icon)
        .override(300, 300)
        .downsample(DownsampleStrategy.CENTER_INSIDE)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .skipMemoryCache(false)
        .into(this)
}

fun ImageView.loadImage(path: String) {
    val requestBuilder: RequestBuilder<Drawable> =
        Glide.with(this).asDrawable().sizeMultiplier(0.1f)
    Glide.with(this).load(File(path)).thumbnail(requestBuilder)
        .into(this)
}
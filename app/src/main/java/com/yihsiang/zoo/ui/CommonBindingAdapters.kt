package com.yihsiang.zoo.ui

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun setImage(imageView: ImageView, url: String?) {
    url ?: return
    Glide.with(imageView)
        .load(url)
        .into(imageView)
}

@BindingAdapter("visibleIf")
fun visibleIf(view: View, predicate: Boolean?) {
    predicate ?: return
    view.isVisible = predicate
}
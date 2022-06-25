package com.yihsiang.zoo.ui.detail

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.yihsiang.zoo.model.AnimalDetail

@BindingAdapter("animalDetail")
fun setAnimalDetailText(textView: TextView, detail: AnimalDetail?) {
    detail ?: return
    textView.text = if (detail.titleResource != -1) {
        textView.context.getString(detail.titleResource)
    } else {
        detail.title
    }
}
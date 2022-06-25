package com.yihsiang.zoo.utils

import android.content.res.Resources
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LiveData<Event<T>>.eventObserve(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, EventObserver { observer(it) })
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Fragment.toast(@StringRes message: Int) {
    Toast.makeText(
        requireContext().applicationContext,
        message,
        Toast.LENGTH_SHORT
    ).show()
}
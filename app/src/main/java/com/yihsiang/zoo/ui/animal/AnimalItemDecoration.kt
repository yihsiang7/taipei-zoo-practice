package com.yihsiang.zoo.ui.animal

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

class AnimalItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val divider: Drawable?

    init {
        val ta = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        divider = ta.getDrawable(0)
        ta.recycle()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        divider ?: return

        val left = parent.left
        val right = parent.width - parent.paddingRight

        (0 until parent.childCount).forEach { index ->
            val child = parent.getChildAt(index)

            if (parent.getChildViewHolder(child) !is AnimalViewHolder) return@forEach

            val params = child.layoutParams as? RecyclerView.LayoutParams ?: return@forEach
            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight

            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }
}
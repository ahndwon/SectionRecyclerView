package com.ahndwon.sectionrecyclerview

import android.view.View

interface Sectionable {
    fun bind(itemView: View)
    fun viewType(): Int

    interface Header : Sectionable

    interface Child : Sectionable {
        var onDragTouch: (() -> Unit)?

        fun onDragTouch()
    }
}

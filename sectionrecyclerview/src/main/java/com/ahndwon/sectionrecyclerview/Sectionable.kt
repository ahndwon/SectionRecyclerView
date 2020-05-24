package com.ahndwon.sectionrecyclerview

import android.view.MotionEvent
import android.view.View

interface Sectionable {
    fun bind(itemView: View)
    fun viewType(): Int
    fun getSection(): String

    interface Header : Sectionable

    interface Child : Sectionable {
        fun setDragTouch(itemView: View, onDragTouch: (MotionEvent) -> Unit)
    }
}

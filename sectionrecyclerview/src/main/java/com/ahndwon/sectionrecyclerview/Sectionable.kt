package com.ahndwon.sectionrecyclerview

import android.view.MotionEvent
import android.view.View

interface Sectionable {
    fun bind(itemView: View, isDragOn: Boolean)
    fun getViewType(): Int
    fun getSection(): String
    fun <T> getItem(): T?

    interface Header : Sectionable

    interface Child : Sectionable {
        fun setDragTouch(itemView: View, onDragTouch: (MotionEvent) -> Unit)
    }
}
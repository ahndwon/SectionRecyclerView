package com.ahndwon.sectionrecyclerview

import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.item_child_two.view.*

class ChildTwo(private val body: String) : Sectionable.Child {

    override fun setDragTouch(itemView: View, onDragTouch: (MotionEvent) -> Unit) {
        itemView.dragButton.setOnTouchListener { _, event ->
            onDragTouch(event)
            false
        }
    }

    override fun bind(itemView: View) {
        itemView.bodyTextView.text = body
    }

    override fun viewType(): Int = VIEW_TYPE

    companion object {
        const val VIEW_TYPE = 12
    }

}
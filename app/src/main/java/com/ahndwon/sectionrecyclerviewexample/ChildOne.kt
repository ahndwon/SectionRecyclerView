package com.ahndwon.sectionrecyclerviewexample

import android.view.MotionEvent
import android.view.View
import com.ahndwon.sectionrecyclerview.Sectionable
import kotlinx.android.synthetic.main.item_child_one.view.*

class ChildOne(private val body: String) : Sectionable.Child {

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
        const val VIEW_TYPE = 11
    }

}
package com.ahndwon.sectionrecyclerviewexample

import android.view.MotionEvent
import android.view.View
import com.ahndwon.sectionrecyclerview.Sectionable
import kotlinx.android.synthetic.main.item_child_two.view.*

class ChildTwo(private val body: String) : Sectionable.Child {

    val sectionId = "ChildTwo"

    override fun setDragTouch(itemView: View, onDragTouch: (MotionEvent) -> Unit) {
        itemView.dragButton.setOnTouchListener { _, event ->
            onDragTouch(event)
            false
        }
    }

    override fun bind(itemView: View) {
        itemView.bodyTextView.text = body
    }

    override fun getViewType(): Int = VIEW_TYPE

    override fun getSection(): String = sectionId

    companion object {
        const val VIEW_TYPE = 12
    }

}
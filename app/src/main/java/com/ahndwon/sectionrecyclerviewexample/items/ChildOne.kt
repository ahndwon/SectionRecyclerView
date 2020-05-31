package com.ahndwon.sectionrecyclerviewexample.items

import android.view.MotionEvent
import android.view.View
import com.ahndwon.sectionrecyclerview.Sectionable
import kotlinx.android.synthetic.main.item_child_one.view.*

class ChildOne(private val item: ListItem) : Sectionable.Child {

    val sectionId = "ChildOne"

    override fun setDragTouch(itemView: View, onDragTouch: (MotionEvent) -> Unit) {
        itemView.dragButton.setOnTouchListener { _, event ->
            onDragTouch(event)
            false
        }
    }

    override fun bind(itemView: View) {
        itemView.bodyTextView.text = "${item.text}, ${item.id}"
    }

    override fun getViewType(): Int =
        VIEW_TYPE

    override fun getSection(): String = sectionId

    @Suppress("UNCHECKED_CAST")
    override fun <T> getItem(): T {
        return item as T
    }

    companion object {
        const val VIEW_TYPE = 11
    }

}
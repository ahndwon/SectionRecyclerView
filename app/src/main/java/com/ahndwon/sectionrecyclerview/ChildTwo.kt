package com.ahndwon.sectionrecyclerview

import android.view.View
import kotlinx.android.synthetic.main.item_child_one.view.*

class ChildTwo(private val body: String) : Sectionable.Child {

    override var onDragTouch: (() -> Unit)? = null

    override fun onDragTouch() {

    }

    override fun bind(itemView: View) {
        itemView.bodyTextView.text = body

    }

    override fun viewType(): Int = VIEW_TYPE

    companion object {
        const val VIEW_TYPE = 12
    }

}
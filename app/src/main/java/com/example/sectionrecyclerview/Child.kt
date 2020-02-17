package com.example.sectionrecyclerview

import android.view.View
import kotlinx.android.synthetic.main.item_child.view.*

class Child(private val body: String) : Section {

    override fun bind(itemView: View) {
        itemView.bodyTextView.text = body
    }

    override fun viewType(): Int = 2

}
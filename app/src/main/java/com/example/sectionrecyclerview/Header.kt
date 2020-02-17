package com.example.sectionrecyclerview

import android.view.View
import kotlinx.android.synthetic.main.item_header.view.*

class Header(private val header: String) : Section {
    override fun bind(itemView: View) {
        itemView.headerTextView.text = header
    }

    override fun viewType(): Int = 1

}
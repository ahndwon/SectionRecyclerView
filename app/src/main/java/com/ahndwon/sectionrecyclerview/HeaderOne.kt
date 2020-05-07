package com.ahndwon.sectionrecyclerview

import android.view.View
import kotlinx.android.synthetic.main.item_header_one.view.*

class HeaderOne(private val header: String) : Sectionable.Header {

    override fun bind(itemView: View) {
        itemView.headerTextView.text = header
    }

    override fun viewType(): Int = VIEW_TYPE

    companion object {
        const val VIEW_TYPE = 21
    }
}
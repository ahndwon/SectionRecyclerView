package com.ahndwon.sectionrecyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_header_one.view.*

class HeaderTwo(private val header: String) : Sectionable.Header {

    override fun bind(itemView: View) {
        itemView.headerTextView.text = header
    }

    override fun viewType(): Int = VIEW_TYPE

    companion object {
        const val VIEW_TYPE = 22
    }
}
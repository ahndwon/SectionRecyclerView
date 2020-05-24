package com.ahndwon.sectionrecyclerviewexample

import android.view.View
import com.ahndwon.sectionrecyclerview.Sectionable
import kotlinx.android.synthetic.main.item_header_one.view.*

class HeaderTwo(private val header: String) : Sectionable.Header {

    val sectionId = "HeaderTwo"

    override fun bind(itemView: View) {
        itemView.headerTextView.text = header
    }

    override fun viewType(): Int = VIEW_TYPE

    override fun getSection(): String = sectionId

    companion object {
        const val VIEW_TYPE = 22
    }
}
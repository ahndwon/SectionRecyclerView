package com.ahndwon.sectionrecyclerviewexample

import android.view.View
import com.ahndwon.sectionrecyclerview.Sectionable
import kotlinx.android.synthetic.main.item_header_one.view.*

class HeaderOne(private val header: String) : Sectionable.Header {

    val sectionId = "HeaderOne"

    override fun bind(itemView: View) {
        itemView.headerTextView.text = header
    }

    override fun viewType(): Int = VIEW_TYPE

    override fun getSection(): String = sectionId

    companion object {
        const val VIEW_TYPE = 21
    }
}
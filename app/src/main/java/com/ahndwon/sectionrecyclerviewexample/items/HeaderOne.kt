package com.ahndwon.sectionrecyclerviewexample.items

import android.view.View
import com.ahndwon.sectionrecyclerview.Sectionable
import kotlinx.android.synthetic.main.item_header_one.view.*

class HeaderOne(private val item: String) : Sectionable.Header {

    val sectionId = "HeaderOne"

    override fun bind(itemView: View, isDragOn: Boolean) {
        itemView.headerTextView.text = item
    }

    override fun getViewType(): Int = VIEW_TYPE

    override fun getSection(): String = sectionId

    @Suppress("UNCHECKED_CAST")
    override fun <T> getItem(): T {
        return item as T
    }

    companion object {
        const val VIEW_TYPE = 21
    }
}
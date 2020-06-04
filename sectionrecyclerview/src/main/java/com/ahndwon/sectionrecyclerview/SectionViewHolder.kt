package com.ahndwon.sectionrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var item: Sectionable? = null

    fun bind(section: Sectionable, isDragOn: Boolean) {
        item = section
        section.bind(itemView, isDragOn)
    }

    fun reBind(isDragOn: Boolean) {
        item?.bind(itemView, isDragOn)
    }

    companion object {
        fun create(parent: ViewGroup, @LayoutRes layoutId: Int): SectionViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            return SectionViewHolder(view)
        }
    }
}
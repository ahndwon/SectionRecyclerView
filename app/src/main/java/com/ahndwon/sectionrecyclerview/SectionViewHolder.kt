package com.ahndwon.sectionrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(section: Sectionable) {
        section.bind(itemView)
    }

    companion object {
        fun create(parent: ViewGroup, @LayoutRes layoutId: Int): SectionViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            return SectionViewHolder(view)
        }
    }
}
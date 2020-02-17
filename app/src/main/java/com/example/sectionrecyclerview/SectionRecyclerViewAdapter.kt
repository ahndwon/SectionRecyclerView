package com.example.sectionrecyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SectionRecyclerViewAdapter : RecyclerView.Adapter<SectionViewHolder>() {
    var items: List<Section> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val layout = when (viewType) {
            1 -> R.layout.item_header
            2 -> R.layout.item_child
            else -> R.layout.item_child
        }
        return SectionViewHolder.create(parent, layout)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.bind(items[position])
    }

}
package com.ahndwon.sectionrecyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SectionRecyclerViewAdapter : RecyclerView.Adapter<SectionViewHolder>(),
    StickyHeaderItemDecoration.StickyHeaderInterface {

    var items: ArrayList<Sectionable> = ArrayList()
    var onDragTouch: ((RecyclerView.ViewHolder) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val layout = when (viewType) {
            HeaderOne.VIEW_TYPE -> R.layout.item_header_one
            HeaderTwo.VIEW_TYPE -> R.layout.item_header_two
            ChildOne.VIEW_TYPE -> R.layout.item_child_one
            ChildTwo.VIEW_TYPE -> R.layout.item_child_two
            else -> R.layout.item_child_one
        }
        return SectionViewHolder.create(parent, layout)
    }

    override fun getItemViewType(position: Int): Int = items[position].viewType()

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        var headerPosition = 0
        var position = itemPosition

        do {
            if (isHeader(position)) {
                headerPosition = position
                break
            }
            position--
        } while (position >= 0)

        return headerPosition
    }

    override fun getHeaderLayout(headerPosition: Int): Int {
        return when (items[headerPosition].viewType()) {
            HeaderOne.VIEW_TYPE -> R.layout.item_header_one
            HeaderTwo.VIEW_TYPE -> R.layout.item_header_two
            else -> R.layout.item_header_one
        }
    }

    override fun bindHeaderData(header: View?, headerPosition: Int) {
        items[headerPosition].bind(header ?: return)
    }

    override fun isHeader(itemPosition: Int): Boolean = items[itemPosition] is Sectionable.Header
}
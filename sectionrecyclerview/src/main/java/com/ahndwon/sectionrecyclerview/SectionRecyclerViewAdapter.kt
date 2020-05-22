package com.ahndwon.sectionrecyclerview

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class SectionRecyclerViewAdapter(private val layoutChooser: LayoutChooser, private val isDragOn: Boolean) :
    RecyclerView.Adapter<SectionViewHolder>(),
    StickyHeaderItemDecoration.StickyHeaderInterface {

    var items: ArrayList<Sectionable> = ArrayList()
    var onDragTouch: ((MotionEvent, RecyclerView.ViewHolder) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder =
        SectionViewHolder.create(parent, layoutChooser.onCreateViewHolder(viewType))

    override fun getItemViewType(position: Int): Int = items[position].viewType()

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val item = items[position]

        holder.bind(item)

        if (isDragOn && item is Sectionable.Child) {
            item.setDragTouch(holder.itemView) { motionEvent ->
                onDragTouch?.invoke(motionEvent, holder)
            }
        }
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

    override fun getHeaderLayout(headerPosition: Int): Int =
        layoutChooser.onGetHeaderLayout(items[headerPosition].viewType())

    override fun bindHeaderData(header: View?, headerPosition: Int) {
        items[headerPosition].bind(header ?: return)
    }

    override fun isHeader(itemPosition: Int): Boolean = items[itemPosition] is Sectionable.Header

    interface LayoutChooser {
        @LayoutRes
        fun onCreateViewHolder(viewType: Int): Int

        @LayoutRes
        fun onGetHeaderLayout(viewType: Int): Int
    }
}
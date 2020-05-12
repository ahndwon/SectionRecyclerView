package com.ahndwon.sectionrecyclerview

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperListener {
    fun onItemDrag(viewHolder: RecyclerView.ViewHolder)
    fun onItemDragStart(viewHolder: RecyclerView.ViewHolder)
    fun onItemDragStop(viewHolder: RecyclerView.ViewHolder)
    fun onItemMove(from: Int, to: Int) : Boolean
    fun onItemSwipe(position: Int)
}
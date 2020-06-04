package com.ahndwon.sectionrecyclerview

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperListener {
    fun onItemDrag(viewHolder: RecyclerView.ViewHolder) {}
    fun onItemDragStart(viewHolder: RecyclerView.ViewHolder) {}
    fun onItemDragStop(viewHolder: RecyclerView.ViewHolder) {}
    fun onBeforeItemMove(from: Int, to: Int): Boolean = false
    fun onItemMove(from: Int, to: Int): Boolean = false
    fun onItemSwipe(position: Int) {}
}
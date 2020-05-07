package com.ahndwon.sectionrecyclerview

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(
    private val listener: ItemTouchHelperListener
) :
    ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
//        val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
//        val swipeFlag = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlag, 0)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

//        val itemView = viewHolder.itemView
//        val backgroundCornerOffset = 20
//
//        val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
//        val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
//        val iconBottom = iconTop + icon.intrinsicHeight
//
//        when {
//            dX > 0 -> {
//                val iconLeft = itemView.left + iconMargin + icon.intrinsicWidth
//                val iconRight = itemView.left + iconMargin
//                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
//
//                background.setBounds(
//                    itemView.left,
//                    itemView.top,
//                    itemView.left + dX.toInt() + backgroundCornerOffset,
//                    itemView.bottom
//                )
//            }
//            dX < 0 -> {
//                val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
//                val iconRight = itemView.right - iconMargin
//                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
//
//                background.setBounds(
//                    itemView.right + dX.toInt() - backgroundCornerOffset,
//                    itemView.top,
//                    itemView.right,
//                    itemView.bottom
//                )
//            }
//            else -> {
//                background.setBounds(0, 0, 0, 0)
//            }
//        }
//        background.draw(c)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean =
        listener.onItemMove(viewHolder.adapterPosition, target.adapterPosition)


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) =
        listener.onItemSwipe(viewHolder.adapterPosition)

}
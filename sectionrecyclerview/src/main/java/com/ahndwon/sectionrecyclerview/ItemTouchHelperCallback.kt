package com.ahndwon.sectionrecyclerview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.use
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(
    context: Context,
    attrs: AttributeSet? = null,
    private val listener: ItemTouchHelperListener
) : ItemTouchHelper.Callback() {

    private val elevation = context.theme.obtainStyledAttributes(
        attrs, R.styleable.SectionRecyclerView, 0, 0
    ).use {
        it.getDimensionPixelSize(0, 0).toFloat()
    }

    private val dimColor = Color.argb(128, 255, 255, 255)
    private val paint = Paint().apply {
        color = dimColor
    }

    private var isDrag = false

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlag, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean =
        listener.onItemMove(viewHolder.adapterPosition, target.adapterPosition)

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) =
        listener.onItemSwipe(viewHolder.adapterPosition)

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val view = viewHolder.itemView
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        view.translationX = dX
        view.translationY = dY

        if (actionState == ACTION_STATE_DRAG) {
            if (!isDrag) listener.onItemDragStart(viewHolder)
            isDrag = true
            listener.onItemDrag(viewHolder)
        }


        if (isCurrentlyActive) {
            ViewCompat.setElevation(view, elevation)
        }

        if (isDrag && !isCurrentlyActive) {
            isDrag = false
            listener.onItemDragStop(viewHolder)
        }
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        drawDimBackground(c, viewHolder, isCurrentlyActive)
    }

    private fun drawDimBackground(
        c: Canvas, viewHolder: RecyclerView.ViewHolder, isCurrentlyActive: Boolean
    ) {
        val view = viewHolder.itemView
        val overItemRect = RectF(0f, 0f, view.width.toFloat(), view.y)
        val underItemRect =
            RectF(0f, view.y + view.height, view.width.toFloat(), c.height.toFloat())

        if (isCurrentlyActive) {
            c.drawRect(overItemRect, paint)
            c.drawRect(underItemRect, paint)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val view = viewHolder.itemView
        view.translationX = 0f
        view.translationY = 0f
        ViewCompat.setElevation(view, 0f)
    }
}
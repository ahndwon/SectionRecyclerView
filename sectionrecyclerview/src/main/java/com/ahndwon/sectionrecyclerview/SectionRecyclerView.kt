package com.ahndwon.sectionrecyclerview

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SectionRecyclerView : RecyclerView, ItemTouchHelperListener {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setTouchHelper(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setTouchHelper(attrs)
    }

    companion object {
        var BOTTOM_CARD_ANIMATE_DURATION = 300L
        const val DIMMER_ID = -100
    }

    var companionView: View? = null
    var companionViewAnimator: CompanionViewAnimator? = null

    var sectionAdapter: SectionRecyclerViewAdapter? = null
        set(value) {
            field = value
            field?.onDragTouch = { motionEvent, viewHolder ->
                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    itemTouchHelper?.startDrag(viewHolder)
                }
            }
            val stickyHeaderItemDecoration = StickyHeaderItemDecoration(
                field as? StickyHeaderItemDecoration.StickyHeaderInterface ?: return
            )

            this.addItemDecoration(stickyHeaderItemDecoration)

            adapter = field
        }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        private var isScroll = false

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == SCROLL_STATE_DRAGGING && !isScroll) {
                companionView?.let {
                    companionViewAnimator?.show(it)
                }

                isScroll = true
            }

            if (newState == SCROLL_STATE_IDLE && isScroll) {
                companionView?.let {
                    companionViewAnimator?.hide(it)
                }

                isScroll = false
            }
        }
    }

    private var callback : ItemTouchHelperCallback? = null

    private var itemTouchHelper : ItemTouchHelper? = null

    init {
        this.addOnScrollListener(onScrollListener)
    }

    private fun setTouchHelper(attrs: AttributeSet?) {
        callback = ItemTouchHelperCallback(context, attrs,this)
        callback?.let {
            itemTouchHelper = ItemTouchHelper(it)
            itemTouchHelper?.attachToRecyclerView(this)
        }
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addCompanionViewDimmer(companion: View, container: ViewGroup) {
        val dimmer = View(context).apply {
            this.id = DIMMER_ID
            this.background = ColorDrawable(ContextCompat.getColor(context, R.color.dimColor))
            this.layoutParams = companion.layoutParams.apply {
                elevation = companion.elevation + 1
            }
        }
        container.addView(dimmer)
    }

    private fun removeCompanionViewDimmer(container: ViewGroup) {
        handler.post {
            container.removeView(container.findViewById(DIMMER_ID))
        }
    }

    override fun onItemDrag(viewHolder: ViewHolder) {}

    override fun onItemDragStart(viewHolder: ViewHolder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addCompanionViewDimmer(
                companionView ?: return,
                this@SectionRecyclerView.parent as? ViewGroup ?: return
            )
        }
    }

    override fun onItemMove(from: Int, to: Int): Boolean {
        sectionAdapter?.let { adapter ->
            val fromItem = adapter.items[from]
            val toItem = adapter.items[to]

            if (fromItem.viewType() != toItem.viewType()) return false

            adapter.items.removeAt(from)
            adapter.items.add(to, fromItem)
            adapter.notifyItemMoved(from, to)
        }

        return true
    }

    override fun onItemSwipe(position: Int) {}

    override fun onItemDragStop(viewHolder: ViewHolder) {
        removeCompanionViewDimmer(this@SectionRecyclerView.parent as? ViewGroup ?: return)
    }
}
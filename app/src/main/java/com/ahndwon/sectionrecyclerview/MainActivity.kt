package com.ahndwon.sectionrecyclerview

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = arrayListOf(
            HeaderOne("HEADER - 1"),
            ChildOne("this is body - 1"),
            ChildOne("this is body - 2"),
            ChildOne("this is body - 3"),
            ChildOne("this is body - 4"),
            HeaderTwo("HEADER - 2"),
            ChildTwo("this is body - 1"),
            ChildTwo("this is body - 1"),
            ChildTwo("this is body - 1"),
            ChildTwo("this is body - 1"),
            ChildTwo("this is body - 1"),
            ChildTwo("this is body - 1"),
            ChildTwo("this is body - 1"),
            ChildTwo("this is body - 1"),
            HeaderOne("HEADER - 3"),
            ChildOne("this is body - 1"),
            ChildOne("this is body - 2"),
            ChildOne("this is body - 3"),
            ChildOne("this is body - 3"),
            HeaderTwo("HEADER - 4"),
            ChildTwo("this is body - 1"),
            ChildTwo("this is body - 2"),
            ChildTwo("this is body - 3"),
            ChildTwo("this is body - 4"),
            ChildTwo("this is body - 4"),
            ChildTwo("this is body - 4"),
            HeaderOne("HEADER - 5"),
            ChildOne("this is body - 1"),
            ChildOne("this is body - 1"),
            ChildOne("this is body - 1"),
            ChildOne("this is body - 1"),
            ChildOne("this is body - 1"),
            ChildOne("this is body - 1"),
            ChildOne("this is body - 1"),
            ChildOne("this is body - 1"),
            ChildOne("this is body - 1"),
            ChildOne("this is body - 1"),
            ChildOne("this is body - 1"),
            ChildOne("this is body - 1"),
            ChildOne("this is body - 1")
        )

        val adapter = SectionRecyclerViewAdapter().apply {
            this.items = items
        }

        val listener = object : ItemTouchHelperListener {
            override fun onItemMove(from: Int, to: Int): Boolean {
                val fromItem = adapter.items[from]
                val toItem = adapter.items[to]

                if (fromItem.viewType() != toItem.viewType()) return false

                adapter.items.removeAt(from)
                adapter.items.add(to, fromItem)
                adapter.notifyItemMoved(from, to)
                return true
            }

            override fun onItemSwipe(position: Int) {}
        }

        val callback = ItemTouchHelperCallback(
            listener
        )

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(sectionRecyclerView)

        sectionRecyclerView.adapter = adapter

        adapter.onDragTouch = { motionEvent, viewHolder ->
            if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                itemTouchHelper.startDrag(viewHolder)
            }
        }

        sectionRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        sectionRecyclerView.addItemDecoration(StickyHeaderItemDecoration(adapter))
    }
}

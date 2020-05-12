package com.ahndwon.sectionrecyclerviewexample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ahndwon.sectionrecyclerview.SectionRecyclerView
import com.ahndwon.sectionrecyclerview.SectionRecyclerViewAdapter
import com.ahndwon.sectionrecyclerview.CompanionViewAnimator
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

        val layoutChooser = object : SectionRecyclerViewAdapter.LayoutChooser {
            override fun onCreateViewHolder(viewType: Int): Int =
                when (viewType) {
                    HeaderOne.VIEW_TYPE -> R.layout.item_header_one
                    HeaderTwo.VIEW_TYPE -> R.layout.item_header_two
                    ChildOne.VIEW_TYPE -> R.layout.item_child_one
                    ChildTwo.VIEW_TYPE -> R.layout.item_child_two
                    else -> R.layout.item_child_one
                }

            override fun onGetHeaderLayout(viewType: Int): Int =
                when (viewType) {
                    HeaderOne.VIEW_TYPE -> R.layout.item_header_one
                    HeaderTwo.VIEW_TYPE -> R.layout.item_header_two
                    else -> R.layout.item_header_one
                }
        }


        val companionAnimator = object : CompanionViewAnimator {
            override fun show(companion: View) {
                companion.animate().translationY(companion.height.toFloat()).duration =
                    SectionRecyclerView.BOTTOM_CARD_ANIMATE_DURATION
            }

            override fun hide(companion: View) {
                companion.animate().translationY(0f).duration =
                    SectionRecyclerView.BOTTOM_CARD_ANIMATE_DURATION
            }
        }

        val adapter = SectionRecyclerViewAdapter(layoutChooser).apply {
            this.items = items
        }

        sectionRecyclerView.companionViewAnimator = companionAnimator
        sectionRecyclerView.sectionAdapter = adapter
        sectionRecyclerView.companionView = bottomCard
    }

}

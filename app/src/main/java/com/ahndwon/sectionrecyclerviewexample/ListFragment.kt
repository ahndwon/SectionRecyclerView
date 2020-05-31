package com.ahndwon.sectionrecyclerviewexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ahndwon.sectionrecyclerview.*
import com.ahndwon.sectionrecyclerviewexample.items.*
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        val adapter = SectionRecyclerViewAdapter(layoutChooser, true).apply {
            this.items = Dummies.items
        }

        adapter.notifyDataSetChanged()

        sectionRecyclerView.companionViewAnimator = companionAnimator
        sectionRecyclerView.sectionAdapter = adapter
        sectionRecyclerView.companionView = bottomCard
        sectionRecyclerView.onItemMoveComparator = object : OnItemMoveSectionableComparator {
            override fun onItemMove(from: Sectionable, to: Sectionable): Boolean {
                if (from is Sectionable.Header || from is Sectionable.Header) return false

                return from.getSection() == to.getSection()
            }
        }

        dialog_button.setOnClickListener {
            ListFragmentDialog(adapter.items).show(childFragmentManager, "")
        }
    }
}
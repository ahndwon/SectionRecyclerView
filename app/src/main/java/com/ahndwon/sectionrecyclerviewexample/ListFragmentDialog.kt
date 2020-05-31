package com.ahndwon.sectionrecyclerviewexample

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.ahndwon.sectionrecyclerview.*
import com.ahndwon.sectionrecyclerviewexample.items.ChildOne
import com.ahndwon.sectionrecyclerviewexample.items.ChildTwo
import com.ahndwon.sectionrecyclerviewexample.items.HeaderOne
import com.ahndwon.sectionrecyclerviewexample.items.HeaderTwo
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragmentDialog(val listItems : ArrayList<Sectionable>) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Black_NoTitleBar)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        return super.onCreateDialog(savedInstanceState)
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
            this.items = listItems
        }

        sectionRecyclerView.companionViewAnimator = companionAnimator
        sectionRecyclerView.sectionAdapter = adapter
        sectionRecyclerView.companionView = bottomCard
        sectionRecyclerView.onItemMoveComparator = object : OnItemMoveSectionableComparator {
            override fun onItemMove(from: Sectionable, to: Sectionable): Boolean {
                if (from is Sectionable.Header || from is Sectionable.Header) return false

                return from.getSection() == to.getSection()
            }
        }
    }
}
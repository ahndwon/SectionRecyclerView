package com.ahndwon.sectionrecyclerviewexample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ahndwon.sectionrecyclerview.*
import com.ahndwon.sectionrecyclerviewexample.items.*
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class ListFragment : Fragment() {

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

    val adapter = SectionRecyclerViewAdapter(layoutChooser, true).apply {
        this.items = Dummies.items
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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



        adapter.notifyDataSetChanged()

        sectionRecyclerView.companionViewAnimator = companionAnimator
        sectionRecyclerView.sectionAdapter = adapter
        sectionRecyclerView.companionView = bottomCard
        sectionRecyclerView.onItemMoveComparator = object : OnItemMoveSectionableComparator {
            override fun compare(from: Int, to: Int): Boolean {

                return adapter.items[from].getSection() == adapter.items[to].getSection()
            }
        }

        dialog_button.setOnClickListener {
            val dialog = ListFragmentDialog(adapter.items)
            dialog.onSaveClick = {
                adapter.items = it
                adapter.notifyDataSetChanged()
            }

            dialog.onCancelClick = {
                adapter.items = it
                adapter.notifyDataSetChanged()
            }
            dialog.show(childFragmentManager, "")
        }

        publishChildren()
    }

    private fun publishChildren() {
        Observable.interval(500L, TimeUnit.MILLISECONDS)
            .subscribe({
                val random = Random(System.currentTimeMillis())
                val randomNum = random.nextInt(0, adapter.items.size)
                val newItem = ListItem("new", "new published item", randomNum)
                RxBus.publish(newItem)
                Log.d("TEST", "interval published : $newItem")

            }, {
                Log.d("TEST", "interval failed : $it")
            })
    }
}
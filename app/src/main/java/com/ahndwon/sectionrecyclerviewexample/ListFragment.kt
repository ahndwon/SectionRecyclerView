package com.ahndwon.sectionrecyclerviewexample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ahndwon.sectionrecyclerview.*
import com.ahndwon.sectionrecyclerviewexample.items.*
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.item_header_one.view.*
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

    private val headerListener by lazy {
        object : SectionRecyclerViewAdapter.HeaderListener {
            override fun onMoveHeader(currentHeader: View, nextHeader: View) {
                headerView.visibility = View.GONE
            }

            override fun onDrawHeader(header: View) {
                val text = header.findViewById<TextView>(R.id.headerTextView).text
                headerView.headerTextView.text = text
                headerView.visibility = View.VISIBLE
                headerView.headerButton.setOnClickListener {
                    Toast.makeText(header.context, "test", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val adapter by lazy {
        SectionRecyclerViewAdapter(layoutChooser, headerListener, true).apply {
            this.items = Dummies.items
        }
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
//            val dialog = ListFragmentDialog(copy(adapter.items))
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

    private fun copy(items: ArrayList<Sectionable>): ArrayList<Sectionable> {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Sectionable::class.java, InterfaceAdapter())
//        gsonBuilder.registerTypeAdapter(ChildOne::class.java, InterfaceAdapter(Sectionable::class.java))
//            .registerTypeAdapter(ChildTwo::class.java, InterfaceAdapter(Sectionable::class.java))
//            .registerTypeAdapter(HeaderOne::class.java, InterfaceAdapter(Sectionable::class.java))
//            .registerTypeAdapter(HeaderTwo::class.java, InterfaceAdapter(Sectionable::class.java))
        val gson = gsonBuilder.create()

        return ArrayList(items.map {
            val json = gson.toJson(it)
            Log.d("copy", "json : $json")
            when(it) {
                is ChildOne -> gson.fromJson(json, ChildOne::class.java)
                is ChildTwo -> gson.fromJson(json, ChildTwo::class.java)
                is HeaderOne -> gson.fromJson(json, HeaderOne::class.java)
                is HeaderTwo -> gson.fromJson(json, HeaderTwo::class.java)
                else -> gson.fromJson(json, ChildOne::class.java)
            } as Sectionable
//            gson.fromJson(json, Sectionable::class.java)
        })
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
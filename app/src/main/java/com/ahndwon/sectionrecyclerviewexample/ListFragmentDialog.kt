package com.ahndwon.sectionrecyclerviewexample

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.ahndwon.sectionrecyclerview.*
import com.ahndwon.sectionrecyclerviewexample.items.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.bottomCard
import kotlinx.android.synthetic.main.fragment_list.sectionRecyclerView

class ListFragmentDialog(val listItems: ArrayList<Sectionable>) : DialogFragment() {

    var onSaveClick: ((ArrayList<Sectionable>) -> Unit)? = null
    var onCancelClick: ((ArrayList<Sectionable>) -> Unit)? = null

    var backupItems: ArrayList<Sectionable> = ArrayList(listItems)

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
        dialog?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

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

        val adapter = SectionRecyclerViewAdapter(layoutChooser, null, true).apply {
            this.items = listItems
        }

        sectionRecyclerView.companionViewAnimator = companionAnimator
        sectionRecyclerView.sectionAdapter = adapter
        sectionRecyclerView.companionView = bottomCard
        sectionRecyclerView.onItemMoveComparator = object : OnItemMoveSectionableComparator {
            override fun compare(from: Int, to: Int): Boolean {

                return adapter.items[from].getSection() == adapter.items[to].getSection()
            }
        }

        RxBus.listen(ListItem::class.java)
//            .flatMap {listItem ->
//                var item: Pair<Int, ChildOne>? = null
//                backupItems.forEachIndexed { index, sectionable ->
//                    if (sectionable is ChildOne) {
//                        if (sectionable.getItem<ListItem>().id == listItem.id) {
//                            item = Pair(index, ChildOne(listItem))
//                            return@flatMap Observable.just(item)
//                        }
//                    }
//                }
//                Log.d("TEST", "item flatMap  : $listItem")
//
//                Observable.just(item!!)
//            }
//            .filter {
//                it != null
//            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ listItem ->
                Log.d("TEST", "item listened")

                adapter.items.forEachIndexed { index, sectionable ->
                    if (sectionable is ChildOne) {
                        if (sectionable.getItem<ListItem>().id == listItem.id) {
                            val listenItem = ChildOne(listItem)
                            adapter.items[index] = listenItem
                            adapter.notifyItemChanged(index)
                            Log.d("TEST", "item listened  : $listenItem")
                        }
                    }
                }

//                listItem?.let {
//                    adapter.items[listItem.first] = listItem.second
//                    adapter.notifyItemChanged(listItem.first)
//                    Log.d("TEST", "item listened  : $listItem")
//                }
            }, {
                Log.d("ListFragmentDialog", "listen failed : $it")
            })

        RxBus.listen(ListItem::class.java)
//            .flatMap { listItem ->
//                var item: Pair<Int, ChildOne>? = null
//                backupItems.forEachIndexed { index, sectionable ->
//                    if (sectionable is ChildOne) {
//                        if (sectionable.getItem<ListItem>().id == listItem.id) {
//                            item = Pair(index, ChildOne(listItem))
//                        }
//                    }
//                }
//                Log.d("TEST", "item flatMap backup  : $listItem")
//                Observable.just(item!!)
//            }
//            .filter {
//                it != null
//            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ listItem ->
                Log.d("TEST", "item listened backup")
                adapter.items.forEachIndexed { index, sectionable ->
                    if (sectionable is ChildOne) {
                        if (sectionable.getItem<ListItem>().id == listItem.id) {
                            val listenItem = ChildOne(listItem)
                            adapter.items[index] = listenItem
                            adapter.notifyItemChanged(index)
                            Log.d("TEST", "item listened  : $listenItem")
                        }
                    }
                }

//                listItem?.let {
//                    backupItems[listItem.first] = listItem.second
//                    Log.d("TEST", "item listened  : $listItem")
//                }
            }, {
                Log.d("ListFragmentDialog", "listen failed : $it")
            })

        save_button.setOnClickListener {
            onSaveClick?.invoke(listItems)
            dismiss()
        }

        cancel_button.setOnClickListener {
            onCancelClick?.invoke(backupItems)
            dismiss()
        }
    }
}
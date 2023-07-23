package com.jcoding.listadaptersample.ui

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.jcoding.listadaptersample.R
import com.jcoding.listadaptersample.data.model.ListItemData
import com.jcoding.listadaptersample.databinding.ActivityMainBinding
import com.jcoding.listadaptersample.ui.list.EndlessRecyclerViewScrollListener
import com.jcoding.listadaptersample.ui.list.ItemListAdapter
import com.jcoding.listadaptersample.ui.list.ListItemSwipeCallback
import com.jcoding.listadaptersample.ui.list.VerticalSpaceRecyclerItemDecoration


class MainActivity : AppCompatActivity() {
    private val listViewModel: ListViewModel by viewModels {
        ListViewModelFactory()
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: ItemListAdapter
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /////////////////////////////////////////////////////////////
        listAdapter = ItemListAdapter()
        setAdapter()
        /////////////////////////////////////////////////////////////
        listAdapter.setCallback(listAdapterCallback)
        binding.refreshDataButton.setOnClickListener {
            scrollListener.resetState()
            listViewModel.refreshList()
        }
        ////////////////////////////////////////////////////////////
        listViewModel.itemListLiveData().observe(this) {
            listAdapter.submitList(it)
        }

    }

    private fun setAdapter() {
        binding.listRecyclerView.addItemDecoration(VerticalSpaceRecyclerItemDecoration(8))
        binding.listRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.listRecyclerView.adapter = listAdapter
        scrollListener = object :
            EndlessRecyclerViewScrollListener(
                binding.listRecyclerView.layoutManager as LinearLayoutManager
            ) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                listViewModel.loadMore()
            }
        }
        binding.listRecyclerView.addOnScrollListener(scrollListener)
        enableSwipeToDeleteAndUndo()
    }


    private val listAdapterCallback = object : ItemListAdapter.Callback {
        override fun onStatusIconClick(item: ListItemData) {
            listViewModel.changeItemStatus(item)
        }

        override fun onDeleteOptionClick(item: ListItemData) {
            listViewModel.removeItem(item)
        }
    }

    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback: ListItemSwipeCallback = object : ListItemSwipeCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                val position = viewHolder.adapterPosition
                val alertDialog: AlertDialog = AlertDialog.Builder(this@MainActivity).apply {
                    setTitle(getString(R.string.title_delete_item_dialog))
                    setPositiveButton(getString(R.string.label_delete_item)
                    ) { _, id ->
                        listViewModel.removeItem(listAdapter.currentList[position])
                    }
                    setNegativeButton(getString(R.string.label_cancel_delete_item)
                    ) { _, id ->
                        // User cancelled the dialog
                    }
                    setOnDismissListener {
                        listAdapter.notifyItemChanged(position)
                    }
                }.create()
                alertDialog.show()
            }
        }
        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(binding.listRecyclerView)
    }

}
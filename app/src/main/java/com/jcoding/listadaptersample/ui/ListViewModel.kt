package com.jcoding.listadaptersample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcoding.listadaptersample.R
import com.jcoding.listadaptersample.data.ListItemMockDataSource
import com.jcoding.listadaptersample.data.model.ListItemData
import com.jcoding.listadaptersample.data.model.ListItemStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ListViewModel(private val dataSource: ListItemMockDataSource) : ViewModel() {


    private var itemList  = mutableListOf<ListItemData>()
    private val _itemListLiveData = MutableLiveData<List<ListItemData>>()
    fun itemListLiveData(): LiveData<List<ListItemData>> = _itemListLiveData

    private var fetchListJob: Job? = null
    private fun fetchListData() {
        fetchListJob?.cancel()
        fetchListJob = viewModelScope.launch {
            val list = dataSource.getAdapterItemList()
            itemList.addAll(list)
            _itemListLiveData.value = itemList
        }
    }

    fun changeItemStatus(requestedItem: ListItemData) {
        viewModelScope.launch(Dispatchers.Default) {
            val newList = itemList.toMutableList()
            for (i in 0 until newList.size) {
                val listItem = newList[i]
                if (listItem.id == requestedItem.id) {
                    val updatedStatusIcon : Int
                    val updatedStatus : ListItemStatus
                    //toggle the status and status icon
                        if (listItem.status == ListItemStatus.PLAY) {
                            updatedStatus = ListItemStatus.PAUSE
                            updatedStatusIcon = R.drawable.ic_pause
                        } else {
                            updatedStatus = ListItemStatus.PLAY
                            updatedStatusIcon = R.drawable.ic_play
                        }
                    newList[i] = listItem.copy(
                        imageResId = updatedStatusIcon,
                        status = updatedStatus
                    )
                    break
                }
            }
            itemList = newList
            _itemListLiveData.postValue(itemList)
        }
    }

    fun removeItem(requestedItem: ListItemData) {
        viewModelScope.launch(Dispatchers.Default) {
            val newList = itemList.toMutableList()
            for (i in 0 until newList.size) {
                if (newList[i].id == requestedItem.id) {
                    newList.removeAt(i)
                    break
                }
            }
            itemList = newList
            _itemListLiveData.postValue(itemList)
        }
    }


    fun refreshList(){
        itemList = itemList.toMutableList().apply { clear() }
        fetchListData()
    }



    init {
        fetchListData()
    }
}
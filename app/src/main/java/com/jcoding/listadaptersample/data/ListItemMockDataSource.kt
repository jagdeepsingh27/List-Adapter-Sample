package com.jcoding.listadaptersample.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jcoding.listadaptersample.R
import com.jcoding.listadaptersample.data.model.ListItemData
import com.jcoding.listadaptersample.data.model.ListItemStatus
import com.jcoding.listadaptersample.data.model.MockDataItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.nio.charset.Charset


class ListItemMockDataSource(private val context: Context) {
    companion object{
        private const val ITEM_PER_PAGE = 10
    }
    private var rawDataList: ArrayList<MockDataItemModel>? = null


    private fun initMockRawData() {
        try {
            val inputStream = context.assets.open("mockdata.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charset.forName("UTF-8"))
            rawDataList = Gson().fromJson<List<MockDataItemModel>>(
                json,
                object : TypeToken<List<MockDataItemModel>>() {}.type
            ) as ArrayList


        } catch (e: IOException) {
            println(e.message)
        }
    }

    suspend fun getAdapterItemList(pageNo : Int = 1) : ArrayList<ListItemData> = withContext(Dispatchers.IO) {
        if(rawDataList == null){
            initMockRawData()
        }
        val dataList = rawDataList ?: return@withContext arrayListOf()
        val list = ArrayList<ListItemData>()

        val startIndex = (pageNo - 1) * ITEM_PER_PAGE

        if(startIndex > dataList.size -1 ){
            return@withContext arrayListOf()
        }

        var endIndex = startIndex + ITEM_PER_PAGE
        if(endIndex > dataList.size){
            endIndex = dataList.size
        }
        endIndex -= 1

        for(i in startIndex..endIndex ){
            val item = dataList[i]
            list.add(generateFakeItem(i,item))
        }
        return@withContext list
    }





    private fun generateFakeItem(_id: Int, data: MockDataItemModel): ListItemData {
        val randomNumber = (1..10).random()
        return ListItemData(
            id = _id.toString(),
            title = data.title,
            amount = (0..100).random().toFloat(),
            imageResId = if (_id % randomNumber == 0) R.drawable.ic_play else R.drawable.ic_pause,
            description = data.description,
            status = if (_id % randomNumber == 0) ListItemStatus.PLAY else ListItemStatus.PAUSE,
        )
    }


}
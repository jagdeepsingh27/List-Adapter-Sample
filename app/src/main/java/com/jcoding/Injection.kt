package com.jcoding

import android.annotation.SuppressLint
import android.content.Context
import com.jcoding.listadaptersample.data.ListItemMockDataSource

object Injection {
    //this is called from the Application class for initialized required content
    fun init(context: Context) {
        initListDataSource(context.applicationContext)
    }


    @SuppressLint("StaticFieldLeak")
    private var listDataSource: ListItemMockDataSource? = null
    fun requiredListDataSource() = requireNotNull(listDataSource)
    private fun initListDataSource(context: Context) {
        listDataSource = ListItemMockDataSource(context)
    }

}
package com.jcoding.listadaptersample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jcoding.Injection


class ListViewModelFactory() :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListViewModel(
            dataSource = Injection.requiredListDataSource()
        ) as T
    }
}
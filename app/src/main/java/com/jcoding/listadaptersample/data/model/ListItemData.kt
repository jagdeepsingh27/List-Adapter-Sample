package com.jcoding.listadaptersample.data.model

data class ListItemData(
    val id : String,
    val title : String ?,
    val imageResId : Int,
    val description : String ?,
    val amount : Float,
    val status : ListItemStatus
)

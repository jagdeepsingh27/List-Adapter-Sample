package com.jcoding.listadaptersample.ui.list

import com.jcoding.listadaptersample.data.model.ListItemData

internal fun ListItemData.diff(other: ListItemData): ListItemPayloadDiff {
    return ListItemPayloadDiff(
        isTitleChanged = title != other.title,
        isProfileImageChanged = imageResId != other.imageResId,
        isAmountChanged = amount != other.amount,
        isDescriptionChanged = description != other.description,
        isStatusChanged = status != other.status
    )
}
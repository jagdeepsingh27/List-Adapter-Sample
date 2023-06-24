package com.jcoding.listadaptersample.ui.list

data class ListItemPayloadDiff(
    val isTitleChanged: Boolean,
    val isDescriptionChanged: Boolean,
    val isProfileImageChanged: Boolean,
    val isAmountChanged: Boolean,
    val isStatusChanged: Boolean
) {
    fun hasDifference(): Boolean {
        return isTitleChanged || isDescriptionChanged || isProfileImageChanged ||
                isAmountChanged || isStatusChanged
    }

    operator fun plus(other: ListItemPayloadDiff): ListItemPayloadDiff = copy(
        isTitleChanged = isTitleChanged || other.isTitleChanged,
        isDescriptionChanged = isDescriptionChanged || other.isDescriptionChanged,
        isProfileImageChanged = isProfileImageChanged || other.isProfileImageChanged,
        isAmountChanged = isAmountChanged || other.isAmountChanged,
        isStatusChanged = isStatusChanged || other.isStatusChanged
    )

    companion object {
        fun getFullPayload() = ListItemPayloadDiff(
            isTitleChanged = true,
            isDescriptionChanged = true,
            isProfileImageChanged = true,
            isAmountChanged = true,
            isStatusChanged = true
        )

        fun getEmptyPayload() = ListItemPayloadDiff(
            isTitleChanged = false,
            isDescriptionChanged = false,
            isProfileImageChanged = false,
            isAmountChanged = false,
            isStatusChanged = false
        )
    }
}
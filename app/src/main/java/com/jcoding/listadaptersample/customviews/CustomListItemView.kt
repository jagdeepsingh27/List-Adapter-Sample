package com.jcoding.listadaptersample.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.jcoding.listadaptersample.data.model.ListItemData
import com.jcoding.listadaptersample.databinding.CustomListItemViewLayoutBinding
import com.jcoding.listadaptersample.ui.list.ListItemPayloadDiff

class CustomListItemView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding: CustomListItemViewLayoutBinding =
        CustomListItemViewLayoutBinding.inflate(LayoutInflater.from(context), this)

    interface Callback {
        fun onStatusIconClick()
    }

    private var callback: Callback? = null

    fun setCallback(_callback: Callback) {
        callback = _callback
    }


    fun setContent(data: ListItemData, payloadDiff: ListItemPayloadDiff) {
        if (payloadDiff.isTitleChanged) {
            binding.titleTextView.text = data.title
        }
        if (payloadDiff.isDescriptionChanged) {
            binding.descriptionTextView.text = data.description
        }
        if (payloadDiff.isProfileImageChanged) {
            binding.statusImageView.setImageResource(data.imageResId)
        }
        if (payloadDiff.isAmountChanged) {
            binding.amountTextView.text = data.amount.toString()
        }
        if (payloadDiff.isStatusChanged) {
            binding.statusTypeTextView.text = data.status.toString()
        }
        binding.statusImageView.setOnClickListener {
            callback?.onStatusIconClick()
        }
    }
}
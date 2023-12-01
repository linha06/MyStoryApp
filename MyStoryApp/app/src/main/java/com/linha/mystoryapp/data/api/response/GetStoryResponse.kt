package com.linha.mystoryapp.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@kotlinx.parcelize.Parcelize
data class GetStoryResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("listStory")
	val listStory: List<ListStoryItem>,

) : Parcelable



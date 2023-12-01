package com.linha.mystoryapp.data.api.response

import com.google.gson.annotations.SerializedName

data class ListAllStory(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("story")
    val story: ListStoryItem
)

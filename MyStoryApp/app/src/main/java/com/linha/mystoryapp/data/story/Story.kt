package com.linha.mystoryapp.data.story

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    var name: String,
    var photo: String,
    var description: String
) : Parcelable

package com.example.cycleme.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String,
    val image: Int,
    val description: String,
): Parcelable
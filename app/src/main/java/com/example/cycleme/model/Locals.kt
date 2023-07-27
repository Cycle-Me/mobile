package com.example.cycleme.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String,
    val image: Int,
    val description: String,
): Parcelable

@Parcelize
data class RecycleItem(
    val id: Int,
    val image: Int,
    val label: String,
    val desc: String,
) : Parcelable

@Parcelize
data class RewardItem(
    val id: Int,
    val image: Int,
    val banner: Int,
    val title: String,
    val points: Int,
    val details: String,
) : Parcelable
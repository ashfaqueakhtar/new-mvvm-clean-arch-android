package com.example.mvvmclean.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val id: Int,
    val name: String,
    val brand: String,
    val price: String,
    val imageUrl: String
) : Parcelable
package com.lucferbux.lucferbux.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

data class Intro(
    val title: String,
    val title_en: String,
    val description: String,
    val description_en: String,
    val url: String,
    val image: String,
    val timestamp: Date,
    val loaded: Boolean,
    var id: String,

    ): Serializable {
    constructor() : this("", "", "", "", "", "", Date(0), false, "")
}
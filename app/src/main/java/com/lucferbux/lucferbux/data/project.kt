package com.lucferbux.lucferbux.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

data class Project(
    val title: String,
    val title_en: String,
    val description: String,
    val description_en: String,
    val link: String,
    val version: String,
    val date: Date,
    var featured: Boolean,
    var tags: String,
    var id: String
    ): Serializable {
    constructor() : this("", "", "", "", "", "", Date(0), false, "", "")
}
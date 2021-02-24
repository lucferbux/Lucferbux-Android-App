package com.lucferbux.lucferbux.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

data class Work(
    val avatar: String,
    val icon: String,
    val name: String,
    val name_en: String,
    val description: String,
    val description_en: String,
    val job: String,
    val job_en: String,
    val loaded: Boolean,
    var importance: String,
    var id: String
    ): Serializable {
    constructor() : this("", "", "", "", "", "", "", "", false, "", "")
}
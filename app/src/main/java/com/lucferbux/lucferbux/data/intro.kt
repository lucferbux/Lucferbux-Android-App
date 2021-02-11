package com.lucferbux.lucferbux.data

import java.io.Serializable
import java.util.*


data class Intro(
    val cols: String,
    val rows: String,
    val description: String,
    val id: String,
    val image: String,
    val timestamp: Date,
    val title: String,
    val url: String
    ): Serializable {
    constructor() : this("", "", "", "", "", Date(0), "", "")
}
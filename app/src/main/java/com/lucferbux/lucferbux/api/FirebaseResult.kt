package com.lucferbux.lucferbux.api

sealed class FirebaseResult<out R> {
    data class Success<out T>(val data: T) : FirebaseResult<T>()
    data class Error(val exception: Exception) : FirebaseResult<Nothing>()
    object Loading : FirebaseResult<Nothing>()
}
package com.lucferbux.lucferbux.ui.aboutme

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lucferbux.lucferbux.FirestoreUtil

class AboutMeViewModelFactory(private val firestore: FirestoreUtil, private val application: Application): ViewModelProvider.Factory {

    /**
     * Creates a new instance of the given `Class`.
     *
     *
     *
     * @param modelClass a `Class` whose instance is requested
     * @param <T>        The type parameter for the ViewModel.
     * @return a newly created ViewModel
    </T> */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AboutMeViewModel::class.java)) {
            return AboutMeViewModel(firestore, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
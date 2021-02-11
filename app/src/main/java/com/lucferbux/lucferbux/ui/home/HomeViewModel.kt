package com.lucferbux.lucferbux.ui.home

import android.app.Application
import androidx.lifecycle.*

import com.lucferbux.lucferbux.FirestoreUtil
import com.lucferbux.lucferbux.api.FirebaseResult
import com.lucferbux.lucferbux.data.Intro
import kotlinx.coroutines.*

class HomeViewModel(val firebase: FirestoreUtil, application: Application) : AndroidViewModel(application) {

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _introData = MutableLiveData<List<Intro>>()

    val introData: LiveData<List<Intro>>
        get() = _introData

    init {
        getIntroDataFromFirestore()
    }

    fun getIntroDataFromFirestore() =
        coroutineScope.launch {
            firebase.getIntroListener().observeForever { data ->
                _introData.value = data
            }
        }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     *
     *
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}

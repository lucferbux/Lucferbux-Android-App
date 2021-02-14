package com.lucferbux.lucferbux.ui.home.homeDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucferbux.lucferbux.FirestoreUtil
import com.lucferbux.lucferbux.data.Intro
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeDetailViewModel(val firebase: FirestoreUtil, application: Application) : AndroidViewModel(application) {

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

    private val _openLink = MutableLiveData<String?>()

    val openLink: LiveData<String?>
        get() = _openLink


    private val _navigateBack = MutableLiveData<String?>()

    val navigateBack: LiveData<String?>
        get() = _navigateBack



    private val _introData = MutableLiveData<Intro>()

    val introData: LiveData<Intro>
        get() = _introData


    fun getDataFromFirestore(introId: String) =
        coroutineScope.launch {
            firebase.getIntroById(introId).observeForever { data ->
                _introData.value = data
            }
        }

    fun prepareBackNavigation(id: String) {
        _navigateBack.value = id
    }

    fun prepareLink(id: String) {
        _openLink.value = id
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedApp is set to null
     */
    fun onBackNavigation() {
        _navigateBack.value = null
    }

    fun onLinkPrepared() {
        _openLink.value = null
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

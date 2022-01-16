package com.lucferbux.lucferbux.ui.aboutme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucferbux.lucferbux.FirestoreUtil
import com.lucferbux.lucferbux.data.Intro
import com.lucferbux.lucferbux.data.Work
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AboutMeViewModel(val firebase: FirestoreUtil, application: Application) : AndroidViewModel(application) {

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()
    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val animFade: Long = 600

    private val _openLink = MutableLiveData<String?>()

    val openLink: LiveData<String?>
        get() = _openLink


    private val _workData = MutableLiveData<List<Work>>()

    val workData: LiveData<List<Work>>
        get() = _workData

    init {
        getJobDataFromFirestore()
    }

    fun getJobDataFromFirestore() =
        coroutineScope.launch {
            firebase.getWorkListener().observeForever { data ->
                _workData.value = data
            }
        }

    fun navigateLinkedin() {
        _openLink.value = "https://www.linkedin.com/in/lucferbux/"
    }

    fun navigateTwitter() {
        _openLink.value = "https://www.twitter.com/lucferbux"
    }

    fun navigateGithub() {
        _openLink.value = "https://github.com/lucferbux"
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

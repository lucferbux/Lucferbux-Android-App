package com.lucferbux.lucferbux.ui.aboutme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class AboutMeViewModel : ViewModel() {

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()
    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val animFade: Long = 600

    private val _openLink = MutableLiveData<String?>()

    val openLink: LiveData<String?>
        get() = _openLink

    fun navigateLinkedin() {
        _openLink.value = "https://www.linkedin.com/mynetwork/"
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

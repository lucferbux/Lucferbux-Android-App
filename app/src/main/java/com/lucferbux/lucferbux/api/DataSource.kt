package com.lucferbux.lucferbux.api

import android.net.Uri
import com.lucferbux.lucferbux.data.Intro

interface DataSource {

    suspend fun getIntro(): FirebaseResult<List<Intro>>

    suspend fun newIntro(intro: Intro): FirebaseResult<Boolean>

    suspend fun updateIntro(intro: Intro): FirebaseResult<Boolean>

    suspend fun deleteIntro(intro: Intro): FirebaseResult<Boolean>

}
package com.lucferbux.lucferbux

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lucferbux.lucferbux.api.DataSource
import com.lucferbux.lucferbux.api.FirebaseResult
import com.lucferbux.lucferbux.data.Intro
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class FirestoreUtil: DataSource {

    private val db: FirebaseFirestore = Firebase.firestore
    private fun getIntroRef(uuid: String): DocumentReference = db.document("intro/${uuid}")

    override suspend fun getIntro(): FirebaseResult<List<Intro>> =
        suspendCoroutine { cont: Continuation<FirebaseResult<List<Intro>>> ->
            db.collection("intro")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    try {
                        cont.resume(FirebaseResult.Success(it.toObjects(Intro::class.java)))
                    } catch (e: Exception) {
                        cont.resume(FirebaseResult.Error(e))
                    }
                }
                .addOnFailureListener {
                    cont.resume(FirebaseResult.Error(it))
                }
        }


    fun getIntroListener(): LiveData<List<Intro>> {
        // TODO: handle error with firebase result
        val introData: MutableLiveData<List<Intro>> = MutableLiveData()
        db.collection("intro")
            .orderBy("timestamp", Query.Direction.DESCENDING).addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                introData.value = null
                return@EventListener
            }

            var savedAddressList : MutableList<Intro> = mutableListOf()
            for (doc in value!!) {
                var addressItem = doc.toObject(Intro::class.java)
                addressItem.id = doc.id
                savedAddressList.add(addressItem)
            }
                introData.value = savedAddressList
        })

        return introData
    }

    fun getIntroById(id: String): LiveData<Intro> {

        val introReference: MutableLiveData<Intro> = MutableLiveData()
        getIntroRef(id).addSnapshotListener(EventListener<DocumentSnapshot> { value, e ->
            if (e != null) {
                introReference.value = null
                return@EventListener
            }
            value?.let {
                var introObject = it.toObject((Intro::class.java))
                introObject!!.id = id
                introReference.value = introObject
            }
        })
        return introReference
    }


    override suspend fun updateIntro(intro: Intro): FirebaseResult<Boolean> =
        suspendCoroutine { cont: Continuation<FirebaseResult<Boolean>> ->
            val introFieldMap = mutableMapOf<String, Any>()
            if (intro.description.isNotBlank()) introFieldMap["description"] = intro.description
            if (intro.image.isNotBlank()) introFieldMap["image"] = intro.image
            if (intro.title.isNotBlank()) introFieldMap["title"] = intro.title
            if (intro.url != null) introFieldMap["url"] = intro.url
            getIntroRef(intro.id).update(introFieldMap)
                .addOnSuccessListener {
                    cont.resume(FirebaseResult.Success(true))
                }
                .addOnFailureListener {
                    cont.resume(FirebaseResult.Error(it))
                }
        }


    override suspend fun newIntro(intro: Intro): FirebaseResult<Boolean> =
        suspendCoroutine { cont: Continuation<FirebaseResult<Boolean>> ->
            db.collection("intro")
                .add(intro)
                .addOnSuccessListener {
                    cont.resume(FirebaseResult.Success(true))
                }
                .addOnFailureListener {
                    cont.resume(FirebaseResult.Error(it))
                }
        }

    override suspend fun deleteIntro(intro: Intro): FirebaseResult<Boolean> =
        suspendCoroutine { cont: Continuation<FirebaseResult<Boolean>> ->
            getIntroRef(intro.id)
                .delete()
                .addOnSuccessListener {
                    cont.resume(FirebaseResult.Success(true))
                }
                .addOnFailureListener {
                    cont.resume(FirebaseResult.Error(it))
                }
        }





}
package com.lucferbux.lucferbux

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.lucferbux.lucferbux.api.FirebaseResult
import java.lang.NullPointerException
import java.util.*

object StorageUtil {

    private val storageInstance: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }

    private val storageInstancePath: StorageReference
        get() = storageInstance.reference


    fun uploadAvatarImage(imageBytes: ByteArray, onSuccess: (imagePath: String) -> Unit) {
        val ref = storageInstancePath.child("avatar/${UUID.nameUUIDFromBytes(imageBytes)}")
        ref.putBytes(imageBytes)
            .addOnSuccessListener {
                onSuccess(ref.path)
            }
            .addOnFailureListener {
                val log = it
            }

    }


    fun uploadImageWithUri(
        uri: Uri,
        pathString: String,
        block: ((FirebaseResult<Uri>, Int) -> Unit)?
    ) {
        // Get a reference to store file at photos/<FILENAME>.jpg
        val photoRef = storageInstancePath.child(pathString).child(uri.lastPathSegment!!)
        photoRef.putFile(uri)
            .addOnProgressListener { taskSnapshot ->
                val percentComplete = if (taskSnapshot.totalByteCount > 0) {
                    (100 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
                } else 0

                block?.invoke(FirebaseResult.Loading, percentComplete)
            }.continueWithTask { task ->
                // Forward any exceptions
                if (!task.isSuccessful) {
                    throw task.exception!!
                }
                // Request the public download URL
                photoRef.downloadUrl
            }
            .addOnSuccessListener { block?.invoke(FirebaseResult.Success(it), 100) }
            .addOnFailureListener { block?.invoke(FirebaseResult.Error(it), 0) }
    }



    fun uploadIntroImage(imageBytes: ByteArray, onSuccess: (imagePath: String) -> Unit) {
        val ref = storageInstancePath.child("introImage/${UUID.nameUUIDFromBytes(imageBytes)}")
        ref.putBytes(imageBytes)
            .addOnSuccessListener {
                onSuccess(ref.path)
            }
            .addOnFailureListener {
                val log = it
            }

    }

    fun uploadArticleImage(imageBytes: ByteArray, onSuccess: (imagePath: String) -> Unit) {
        val ref = storageInstancePath.child("patentImage/${UUID.nameUUIDFromBytes(imageBytes)}")
        ref.putBytes(imageBytes)
            .addOnSuccessListener {
                onSuccess(ref.path)
            }
            .addOnFailureListener {
                val log = it
            }

    }

}
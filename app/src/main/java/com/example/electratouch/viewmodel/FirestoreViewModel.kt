package com.example.electratouch.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.electratouch.models.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val TAG = "FIRESTORE"

@HiltViewModel
class FirestoreViewModel @Inject constructor() : ViewModel() {

    enum class FireStoreStatus {
        LOADING,
        SUCCESS,
        ERROR,
    }

    private val db = Firebase.firestore

    private val _userList = MutableLiveData<List<User>>()
    val chapterList: LiveData<List<User>> = _userList

    private val _firestoreStatus = MutableLiveData<FireStoreStatus>()
    val fireStoreStatus: LiveData<FireStoreStatus> = _firestoreStatus

    fun loadUser() {
        _firestoreStatus.value = FireStoreStatus.LOADING
        try {
            val chapterDocumentRef = db.collection("users")
            chapterDocumentRef.get()
                .addOnSuccessListener {
                    if (!it.isEmpty) {
                        val users = it.documents.map { document ->
                            val name = document.getString("name") ?: ""
                            val gender = document.getString("gender") ?: ""
                            val age =
                                document.getLong("age")?.toInt() ?: 0 // Get age as an integer
                            val fingerID = document.getLong("fingerID")?.toInt() ?: 0
                            User(name = name, gender = gender, age = age, fingerID = fingerID)
                        }
                        _userList.value = users
                        _firestoreStatus.value = FireStoreStatus.SUCCESS
                    }
                }
                .addOnFailureListener {
                    _firestoreStatus.value = FireStoreStatus.ERROR
                    Log.d(TAG, "get failed with ", it)
                }
            // Handler is used to send messages between threads
            // post() method is used to send message to the handler
            //Looper is a class that's responsible for processing messages in a thread
            // The Looper.getMainLooper() method returns the Looper associated with the main thread of the current process.
            /* The Handler constructor that you're referring to takes two arguments: the Looper instance that the handler
            should be associated with, and a callback that should be executed when a message is received. */
            //By associating the Handler with the main thread's Looper, any messages that are processed by the handler will be executed on the main thread
            Handler(Looper.getMainLooper()).postDelayed({
                if (_firestoreStatus.value == FireStoreStatus.LOADING) {
                    _firestoreStatus.value = FireStoreStatus.ERROR
                    _userList.value = listOf()
                }
            }, 10000)
        } catch (e: java.lang.Exception) {
            _firestoreStatus.value = FireStoreStatus.ERROR
            _userList.value = listOf()
        }
    }
}


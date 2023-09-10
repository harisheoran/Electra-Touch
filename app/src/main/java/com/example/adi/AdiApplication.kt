package com.example.adi

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AdiApplication : Application() {
    companion object {
        lateinit var myContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        myContext = this
    }
}
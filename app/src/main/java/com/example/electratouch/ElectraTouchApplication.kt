package com.example.electratouch

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ElectraTouchApplication : Application() {
    companion object {
        lateinit var myContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        myContext = this
    }
}
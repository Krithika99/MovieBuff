package com.ksas.moviebuff

import android.app.Application
import com.ksas.moviebuff.screens.SharedPreferenceManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferenceManager.initialize(applicationContext)
    }
}
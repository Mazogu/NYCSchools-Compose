package com.example.nycschools_compose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class NYCSchoolApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}
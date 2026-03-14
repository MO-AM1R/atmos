package com.example.atmos.app

import android.app.Application
import com.example.atmos.utils.AppConstants
import com.mapbox.common.MapboxOptions
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Atmos : Application(){
    override fun onCreate() {
        super.onCreate()
        MapboxOptions.accessToken = AppConstants.MAP_PUBLIC_API_KEY
    }
}
package com.pactsafe.demo

import android.app.Application
import com.pactsafe.pactsafeandroidsdk.PSApp

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        PSApp.init(
            BuildConfig.SITE_ACCESS_ID,
            BuildConfig.GROUP_KEY,
            this,
            debug = true,
            testData = true
        )

        PSApp.preload()
    }
}
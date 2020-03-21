package com.pactsafe.demo

import android.app.Application
import com.pactsafe.pactsafeandroidsdk.PSApp

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        PSApp.init("790d7014-9806-4acc-8b8a-30c4987f3a95", this, true)
        PSApp.preload("example-mobile-app-group")
    }


}
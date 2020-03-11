package com.pactsafe.pactsafeandroidsdk

import android.content.Context
import com.pactsafe.pactsafeandroidsdk.data.ActivityService
import com.pactsafe.pactsafeandroidsdk.util.injector
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

object PSApp {

    private var siteAccessId: String? = null
    private var appContext: Context? = null

    fun init(siteAccessId: String, context: Context) {
        this.siteAccessId = siteAccessId
        this.appContext = context
        startKoin {
            androidLogger()
            androidContext(context)
            modules(appModule)
        }
    }

    fun preload(groupKey: String) {
        val activityService: ActivityService = injector()

    }
}
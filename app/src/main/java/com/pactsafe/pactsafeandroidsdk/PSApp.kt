package com.pactsafe.pactsafeandroidsdk

import android.content.Context
import com.pactsafe.pactsafeandroidsdk.data.ActivityService
import com.pactsafe.pactsafeandroidsdk.util.Outcome
import com.pactsafe.pactsafeandroidsdk.util.injector
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

object PSApp {

    private var siteAccessId: String? = null
    private var appContext: Context? = null

    fun init(siteAccessId: String, context: Context, debug: Boolean = false) {
        this.siteAccessId = siteAccessId
        this.appContext = context
        startKoin {
            androidLogger()
            androidContext(context)
            modules(appModule)
        }

        if (debug) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun <T> logDebug(clazz: Class<T>, message: String?) {
        Timber.d(clazz.simpleName, message)
    }

    fun preload(groupKey: String) {
        val activityService: ActivityService = injector()

        val result = siteAccessId?.let {
            activityService.preloadActivity(groupKey, it)
        } ?: Outcome.Error("There was a problem fetching this request.")

        if (result is Outcome.Value) {
            Timber.e("We have a result: ${result.value.alert_message}")
        } else {

        }
    }
}
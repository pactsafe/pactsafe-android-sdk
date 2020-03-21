package com.pactsafe.pactsafeandroidsdk

import android.content.Context
import com.pactsafe.pactsafeandroidsdk.data.ActivityService
import com.pactsafe.pactsafeandroidsdk.di.appModule
import com.pactsafe.pactsafeandroidsdk.util.Outcome
import com.pactsafe.pactsafeandroidsdk.util.injector
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

object PSApp {

    private var siteAccessId: String? = null
    private var appContext: Context? = null
    var debug: Boolean = false

    fun init(siteAccessId: String, context: Context, debug: Boolean = false) {
        this.siteAccessId = siteAccessId
        this.appContext = context
        this.debug = debug
        startKoin {
            androidLogger()
            androidContext(context)
            modules(appModule)
        }
        Timber.plant(Timber.DebugTree())
    }

    fun <T> logDebug(clazz: Class<T>, message: String?) {
        if (debug) {
            Timber.d("***${clazz.simpleName} : $message***")
        }
    }

    fun preload(groupKey: String): Outcome<Boolean> {
        val activityService: ActivityService = injector()

        val result = siteAccessId?.let {
            activityService.preloadActivity(groupKey, it)
        } ?: Outcome.Error("There was a problem fetching this request.")

        return if (result is Outcome.Value) {
            Outcome.of(true)
        } else {
            logDebug(this::class.java, "Error Fetching Data")
            Outcome.error("")
        }
    }
}
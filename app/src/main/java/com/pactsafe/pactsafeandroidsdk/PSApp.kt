package com.pactsafe.pactsafeandroidsdk

import android.content.Context
import com.pactsafe.pactsafeandroidsdk.data.ActivityService
import com.pactsafe.pactsafeandroidsdk.util.Outcome
import com.pactsafe.pactsafeandroidsdk.util.injector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

object PSApp {

    private var siteAccessId: String? = null
    private var appContext: Context? = null
    private var scope: CoroutineScope? = null

    fun init(siteAccessId: String, context: Context, scope: CoroutineScope) {
        this.siteAccessId = siteAccessId
        this.appContext = context
        this.scope = scope
        startKoin {
            androidLogger()
            androidContext(context)
            modules(appModule)
        }
    }

    fun preload(groupKey: String) {
        val activityService: ActivityService = injector()

        scope?.launch {
            val result = activityService.preloadActivity(groupKey, siteAccessId ?: return@launch)
            if (result is Outcome.Value) {
                Timber.e("We have a result: ${result.value.alert_message}")
            } else {

            }
        }
    }
}
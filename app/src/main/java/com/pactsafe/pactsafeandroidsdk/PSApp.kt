package com.pactsafe.pactsafeandroidsdk

import android.content.Context
import com.pactsafe.pactsafeandroidsdk.data.ActivityService
import com.pactsafe.pactsafeandroidsdk.data.ApplicationPreferences
import com.pactsafe.pactsafeandroidsdk.data.handleThrowable
import com.pactsafe.pactsafeandroidsdk.di.appModule
import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import com.pactsafe.pactsafeandroidsdk.util.injector
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

object PSApp {

    private var siteAccessId: String? = null
    private var appContext: Context? = null
    private var testData: Boolean = false
    private val disposables = CompositeDisposable()
    var preload: Boolean = false
    var hasPreloaded: Boolean = false
    var hasPreloadedObservable: BehaviorSubject<PSGroup> = BehaviorSubject.create()
    var debug: Boolean = false

    fun init(siteAccessId: String, context: Context, debug: Boolean = false, testData: Boolean = false) {
        this.siteAccessId = siteAccessId
        this.appContext = context
        this.debug = debug
        this.testData = testData
        startKoin {
            androidLogger()
            androidContext(context)
            modules(appModule)
        }
        Timber.plant(Timber.DebugTree())
    }

    fun preload(groupKey: String) {
        val appPreferences: ApplicationPreferences = injector()
        appPreferences.psGroupKey = groupKey
        appPreferences.siteAccessId = siteAccessId
        preload = true

        val activityService: ActivityService = injector()

        siteAccessId?.let { siteAccessId ->
            disposables.add(
                activityService.preloadActivity(groupKey, siteAccessId)
                    .subscribe({
                        setPreLoaded(it)
                    }, {
                        handleThrowable(it)
                    })
            )
        } ?: Timber.e("Site Access Id is empty.")

    }

    private fun setPreLoaded(psGroup: PSGroup) {
        val appPreferences: ApplicationPreferences = injector()
        appPreferences.group = psGroup
        hasPreloaded = true
        hasPreloadedObservable.onNext(psGroup)
    }

    fun endSubscriptions() {
        disposables.clear()
    }
}

object PSLogger {
    fun debugLog(message: String?) {
        if (PSApp.debug) {
            Timber.d(message)
        }
    }

    fun errorLog(throwable: Throwable, message: String?) {
        Timber.e(throwable, message)
    }

    fun warningLog(message: String?) {
        Timber.w(message)
    }
}
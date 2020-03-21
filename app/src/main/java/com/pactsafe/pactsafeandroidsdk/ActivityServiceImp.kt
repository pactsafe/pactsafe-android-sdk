package com.pactsafe.pactsafeandroidsdk

import com.pactsafe.pactsafeandroidsdk.data.ActivityAPI
import com.pactsafe.pactsafeandroidsdk.data.ActivityService
import com.pactsafe.pactsafeandroidsdk.data.ApplicationPreferences
import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import com.pactsafe.pactsafeandroidsdk.models.PSResponse
import com.pactsafe.pactsafeandroidsdk.util.Outcome
import com.pactsafe.pactsafeandroidsdk.util.injector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ActivityServiceImp(private val activityAPI: ActivityAPI) :
    ActivityService {

    override fun preloadActivity(groupKey: String, siteAccessKey: String): Outcome<PSGroup> {

        val appPreferences: ApplicationPreferences = injector()
        val result = activityAPI.preload(siteAccessKey, groupKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { PSApp.logDebug(this::class.java, it.localizedMessage)}
            .blockingSingle()

        return (if (result.isSuccessful) {
            val psGroup = result.body() as PSGroup
            with(appPreferences) {
                this.siteAccessId = siteAccessKey
                this.group = psGroup
                this.groupKey = groupKey
            }
            Outcome.of(psGroup)
        } else {
            Outcome.error(result.errorBody() as PSResponse)
        })
    }
}
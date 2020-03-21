package com.pactsafe.pactsafeandroidsdk.data

import com.pactsafe.pactsafeandroidsdk.PSApp
import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import com.pactsafe.pactsafeandroidsdk.util.Outcome
import com.pactsafe.pactsafeandroidsdk.util.injector
import io.reactivex.schedulers.Schedulers

class ActivityServiceImp(private val activityAPI: ActivityAPI) :
    ActivityService {

    override fun preloadActivity(groupKey: String, siteAccessKey: String): Outcome<PSGroup> {

        val appPreferences: ApplicationPreferences = injector()
        val result = activityAPI.preload(siteAccessKey, groupKey)
            .subscribeOn(Schedulers.io())
            .blockingGet()

        return if (result.isSuccessful) {
            val psGroup = result.body() as PSGroup
            with(appPreferences) {
                siteAccessId = siteAccessKey
                group = psGroup
                psGroupKey = groupKey
            }
            Outcome.of(psGroup)
        } else {
            PSApp.logDebug(
                this::class.java,
                "There was a problem pre-loading group data"
            )
            Outcome.error("There was a problem pre-loading group data")
        }
    }
}
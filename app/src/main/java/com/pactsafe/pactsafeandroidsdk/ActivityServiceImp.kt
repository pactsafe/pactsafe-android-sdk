package com.pactsafe.pactsafeandroidsdk

import com.pactsafe.pactsafeandroidsdk.data.ActivityAPI
import com.pactsafe.pactsafeandroidsdk.data.ActivityService
import com.pactsafe.pactsafeandroidsdk.data.ApplicationPreferences
import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import com.pactsafe.pactsafeandroidsdk.util.Outcome
import com.pactsafe.pactsafeandroidsdk.util.injector

class ActivityServiceImp(private val activityAPI: ActivityAPI) :
    ActivityService {

    override suspend fun preloadActivity(groupKey: String, siteAccessId: String): Outcome<PSGroup> {

        val appPreferences: ApplicationPreferences = injector()

        val result = activityAPI.preload(siteAccessId, groupKey)
        return (if (result.isSuccessful) {
            val psGroup = result.body() as PSGroup
            with(appPreferences) {
                this.siteAccessId = siteAccessId
                this.group = psGroup
                this.groupKey = groupKey
            }
            Outcome.of(result.body() as PSGroup)
        } else {
            Outcome.error("There was an error ")
        })
    }
}
package com.pactsafe.pactsafeandroidsdk

import com.pactsafe.pactsafeandroidsdk.data.ActivityAPI
import com.pactsafe.pactsafeandroidsdk.data.ActivityService
import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import com.pactsafe.pactsafeandroidsdk.util.Outcome

class ActivityServiceImp(val activityAPI: ActivityAPI) : ActivityService {

    override suspend fun preloadActivity(groupKey: String): Outcome<PSGroup> {
        TODO("Not yet implemented")
    }

}
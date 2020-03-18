package com.pactsafe.pactsafeandroidsdk.data

import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import com.pactsafe.pactsafeandroidsdk.util.Outcome

interface ActivityService {
    suspend fun preloadActivity(groupKey: String, siteAccessKey: String): Outcome<PSGroup>
}
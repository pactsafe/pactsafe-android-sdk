package com.pactsafe.pactsafeandroidsdk.data

import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import com.pactsafe.pactsafeandroidsdk.util.PSResult
import io.reactivex.Single

interface ActivityService {
    fun preloadActivity(groupKey: String, siteAccessKey: String): Single<PSGroup>
}
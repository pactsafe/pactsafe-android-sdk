package com.pactsafe.pactsafeandroidsdk.data

import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import com.pactsafe.pactsafeandroidsdk.models.PSSigner
import com.pactsafe.pactsafeandroidsdk.models.PSSignerID
import com.pactsafe.pactsafeandroidsdk.util.PSResult
import io.reactivex.Single
import retrofit2.Response

interface ActivityService {
    fun preloadActivity(groupKey: String, siteAccessKey: String): Single<PSGroup>
    fun sendActivity(signer: PSSigner, group: PSGroup?): Single<Response<Unit>>
    fun fetchSignedStatus(signer: PSSignerID, group: PSGroup?): Single<Response<Map<String, Boolean>>>
}
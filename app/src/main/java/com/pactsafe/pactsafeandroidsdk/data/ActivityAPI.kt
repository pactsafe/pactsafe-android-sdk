package com.pactsafe.pactsafeandroidsdk.data

import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ActivityAPI {

    @GET("/load/json")
    fun preload(@Query("sid") sid: String, @Query("gkey") gkey: String): Single<Response<PSGroup>>

    @POST("/send")
    fun sendActivity(@QueryMap() queries: Map<String, String?>): Single<Response<Unit>>


}
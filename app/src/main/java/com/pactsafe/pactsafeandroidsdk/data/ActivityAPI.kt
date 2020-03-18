package com.pactsafe.pactsafeandroidsdk.data

import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivityAPI {

    @GET("/load/json")
    suspend fun preload(@Query("sid") sid: String, @Query("gkey") gkey: String): Response<PSGroup>
}
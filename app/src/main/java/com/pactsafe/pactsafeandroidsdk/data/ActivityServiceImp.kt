package com.pactsafe.pactsafeandroidsdk.data

import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ActivityServiceImp(private val activityAPI: ActivityAPI) :
    ActivityService {

    override fun preloadActivity(groupKey: String, siteAccessKey: String): Single<PSGroup> {

        return activityAPI.preload(siteAccessKey, groupKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.body() }
    }
}
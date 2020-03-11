package com.pactsafe.pactsafeandroidsdk

import com.pactsafe.pactsafeandroidsdk.data.ActivityService
import org.koin.dsl.module

val appModule = module {

    single<ActivityService> { ActivityServiceImp(get()) }

    //TODO implement the retrofit instance
}
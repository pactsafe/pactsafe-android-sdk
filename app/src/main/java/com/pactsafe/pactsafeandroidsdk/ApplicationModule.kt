package com.pactsafe.pactsafeandroidsdk

import com.pactsafe.pactsafeandroidsdk.data.ActivityAPI
import com.pactsafe.pactsafeandroidsdk.data.ActivityService
import com.pactsafe.pactsafeandroidsdk.data.ApplicationPreferences
import com.pactsafe.pactsafeandroidsdk.data.ApplicationPreferencesImp
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single<ActivityService> { ActivityServiceImp(get()) }
    single<ActivityAPI> { getRetrofitApi(createOkHttpClient(), BuildConfig.PS_BASE_URL) }
    single<ApplicationPreferences> { ApplicationPreferencesImp(androidContext()) }

}

fun createOkHttpClient(authenticate: Boolean = false): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)

    if (authenticate) {
        client.addInterceptor(AuthenticationInterceptor())
    }
    return client.build()
}

inline fun <reified T> getRetrofitApi(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}

class AuthenticationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader("Authentication", "Bearer <api token>")
                .build()
        )
    }
}
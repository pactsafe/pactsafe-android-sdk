package com.pactsafe.pactsafeandroidsdk

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.pactsafe.pactsafeandroidsdk.data.ActivityService
import com.pactsafe.pactsafeandroidsdk.data.ApplicationPreferences
import com.pactsafe.pactsafeandroidsdk.data.handleThrowable
import com.pactsafe.pactsafeandroidsdk.di.appModule
import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import com.pactsafe.pactsafeandroidsdk.models.PSSigner
import com.pactsafe.pactsafeandroidsdk.models.PSSignerID
import com.pactsafe.pactsafeandroidsdk.ui.PSClickWrapActivity
import com.pactsafe.pactsafeandroidsdk.util.injector
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import retrofit2.Response
import timber.log.Timber

object PSApp {

    private var siteAccessId: String? = null
    private lateinit var appContext: Context
    private var testData: Boolean = false
    private var groupKey: String = ""
    private val disposables = CompositeDisposable()
    var preload: Boolean = false
    var hasPreloaded: Boolean = false
    var hasPreloadedObservable: BehaviorSubject<PSGroup> = BehaviorSubject.create()
    var debug: Boolean = false

    fun init(
        siteAccessId: String,
        groupKey: String,
        context: Context,
        debug: Boolean = false,
        testData: Boolean = false
    ) {
        this.siteAccessId = siteAccessId
        this.groupKey = groupKey
        this.appContext = context
        this.debug = debug
        this.testData = testData
        startKoin {
            androidLogger()
            androidContext(context)
            modules(appModule)
        }
        Timber.plant(Timber.DebugTree())
    }

    fun preload() {
        val appPreferences: ApplicationPreferences = injector()
        appPreferences.psGroupKey = groupKey
        appPreferences.siteAccessId = siteAccessId
        preload = true

        val activityService: ActivityService = injector()

        siteAccessId?.let { siteAccessId ->
            disposables.add(
                activityService.preloadActivity(groupKey, siteAccessId)
                    .subscribe({
                        setPreLoaded(it)
                    }, {
                        handleThrowable(it)
                    })
            )
        } ?: Timber.e("Site Access Id is empty.")

    }

    fun clearPSApp() {
        val appPreferences: ApplicationPreferences = injector()
        appPreferences.group = null
    }

    private fun loadGroupData(): PSGroup? {
        val applicationPreferences: ApplicationPreferences = injector()
        val activityService: ActivityService = injector()

        if (applicationPreferences.group == null) {
            val group = activityService.loadActivity(groupKey, siteAccessId ?: "")
            applicationPreferences.group = group
            return group
        }
        return applicationPreferences.group
    }

    fun loadAcceptanceLanguage(): String {
        val groupData = loadGroupData()
        val acceptanceLanguage = groupData?.acceptance_language?.replace("{{contracts}}", "")
        val contractTitle = groupData.let { group ->
            group?.contract_data?.values?.joinToString(" and ") { "##${it.title}##" }
        }

        return acceptanceLanguage + contractTitle
    }

    fun loadAlertMessage(): String {
        return loadGroupData()?.alert_message ?: ""


    }

    fun getContractLinkClickedList(context: Context, contracts: Map<String, Boolean> = emptyMap()): List<() -> Unit> {

        val contractData = loadGroupData()?.let {
            if (contracts.isNotEmpty()) {
                it.contract_data.filter { (key, _) ->
                    contracts[key] == false
                }
            } else {
                it.contract_data
            }
        } ?: emptyMap()

        return contractData.values.map {
            {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("${loadGroupData()?.legal_center_url}#${it.key}")
                    )
                )
            }
        }

    }

    fun getContractLinks(context: Context, contracts: Map<String, Boolean> = emptyMap()): List<() -> Unit> {
        val activity = context as PSClickWrapActivity

        val contractData = loadGroupData()?.let {
            if (contracts.isNotEmpty()) {
                it.contract_data.filter { (key, _) ->
                    contracts[key] == false
                }
            } else {
                it.contract_data
            }
        } ?: emptyMap()

        return contractData.values.map {
            { activity.onContractLinkClicked(it.title, "${loadGroupData()?.legal_center_url}#${it.key}") }
        }
    }

    private fun setPreLoaded(psGroup: PSGroup) {
        val appPreferences: ApplicationPreferences = injector()
        appPreferences.group = psGroup
        hasPreloaded = true
        hasPreloadedObservable.onNext(psGroup)
    }

    fun endSubscriptions() {
        disposables.clear()
    }

    fun sendAgreed(signer: PSSigner, et: String): Single<Response<Unit>> {
        val activityService: ActivityService = injector()
        return activityService.sendActivity(signer, loadGroupData(), et)
    }

    fun fetchSignedStatus(signer: PSSignerID): Single<Map<String, Boolean>> {
        val activityService: ActivityService = injector()
        return activityService.fetchSignedStatus(signer, loadGroupData()).map { it.body() }
    }

    fun updatedTermsLanguage(contracts: Map<String, Boolean>): CharSequence? {

        val updateLanguage = "We've updated the following: "

        val contractData = loadGroupData()?.let {
            if (contracts.isNotEmpty()) {
                it.contract_data.filter { (key, _) ->
                    contracts[key] == false
                }
            } else {
                it.contract_data
            }
        } ?: emptyMap()

        return updateLanguage + contractData.map { it.value.title }.joinToString(",") { it }
    }
}

object PSLogger {
    fun debugLog(message: String?) {
        if (PSApp.debug) {
            Timber.d(message)
        }
    }

    fun errorLog(throwable: Throwable, message: String?) {
        Timber.e(throwable, message)
    }

    fun warningLog(message: String?) {
        Timber.w(message)
    }
}
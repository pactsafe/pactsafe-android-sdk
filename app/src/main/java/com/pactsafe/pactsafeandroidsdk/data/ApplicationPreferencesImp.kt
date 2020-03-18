package com.pactsafe.pactsafeandroidsdk.data

import android.content.Context
import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import timber.log.Timber

class ApplicationPreferencesImp(val context: Context) : ApplicationPreferences {

    companion object {
        private const val PREFERENCES = "com.pactsafe.PREFERENCES"
        private const val GROUP = "group"
        private const val SITE_ACCESS_ID = "site_access_id"
        private const val GROUP_KEY = "group_key"
    }

    private val preferences
        get() = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    override var group: PSGroup?
        get() {
            return try {
                val stringValue = preferences.getString(GROUP, null) ?: return null
                return Json(JsonConfiguration.Stable).parse(PSGroup.serializer(), stringValue)
            } catch (e: Exception) {
                Timber.e(e, "There was an error fetching this item.")
                null
            }
        }
        set(value) {
            val stringValue = Json(JsonConfiguration.Stable).stringify(PSGroup.serializer(), value ?: return)
            preferences.edit().putString(GROUP, stringValue).apply()
        }
    override var siteAccessId: String
        get() = preferences.getString(SITE_ACCESS_ID, "") ?: ""
        set(value) {
            preferences.edit().putString(SITE_ACCESS_ID, value).apply()
        }
    override var groupKey: String
        get() = preferences.getString(GROUP_KEY, "") ?: ""
        set(value) {
            preferences.edit().putString(GROUP_KEY, value).apply()
        }

}
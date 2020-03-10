package com.pactsafe.pactsafeandroidsdk.models

import com.pactsafe.pactsafeandroidsdk.BuildConfig
import kotlinx.serialization.Serializable

@Serializable
data class PSConnectionData(
    // The client library being used that is sent to PactSafe.
    val clientLibrary: String = "PactSafe Android SDK",
    // The client library version being used that is sent to PactSafe
    val clientVersion: String = BuildConfig.VERSION_NAME,
    // The unique identifier that is unique and usable to this device.
    val deviceFingerprint: String,
    // The mobile device category being used (e.g,. tablet or mobile).
    val environment: String,
    // The operating system and version of the device.
    val operatingSystem: String,
    // The screen resolution of the device.
    val screenResolution: String,
    // The current locale identifier of the device.
    val browserLocale: String,
    // The current time zone identifier of the device.
    val browserTimezone: String,
    // The domain of the page being viewed. Note: This is normally for web pages but is available to be populated if needed.
    val publicDomain: String?,
    // The path of the page being viewed. Note: This is normally for web pages but is available to be populated if needed.
    val pagePath: String?,
    // The query path on the page being viewed. Note: This is normally for web pages but is available to be populated if needed.
    val pageQuery: String?,
    /**
     * The title of the page being viewed. Note: This is normally for web pages but is available to be populated if you'd
     * like to use the title of the screen where the PactSafe activity is occurring.
     */
    val pageTitle: String?,
    // The URL of the page being viewed. Note: This is normally for web pages but is available to be populated if needed.
    val pageUrl: String?,
    // The referred of the page being viewed. Note: This is normally for web pages but is avaialble to be populated if needed.
    val referrer: String?
)
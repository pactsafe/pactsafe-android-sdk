package com.pactsafe.pactsafeandroidsdk.models

import kotlinx.serialization.Serializable

@Serializable
data class PSCustomData(
    // The name of the user's iOS device (e.g., John Doe's Pixel 3 XL).
    val androidDeviceName: String = "",
    // First Name is a reserved property for custom data in PactSafe but can be set.
    val firstName: String? = null,
    // Last Name is a reserved property for custom data in PactSafe but can be set.
    val lastName: String? = null,
    // Company Name is a reserved property for custom data in PactSafe but can be set.
    val companyName: String? = null,
    // Title is a reserved property for custom data in PactSafe but can be set.
    val title: String? = null
)
package com.pactsafe.pactsafeandroidsdk.models

import kotlinx.serialization.Serializable

@Serializable
data class PSContract(
    // The version ID of the published contract.
    val published_version: String,
    // The title of the contract.
    val title: String,
    /**
     * The key of the contract, which can be useful when needing
     * to directly link to it in your legal center.
     */
    val key: String,
    // The change summary (if provided) of the published contract.
    val change_summary: String? = null
)
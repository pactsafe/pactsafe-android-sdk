package com.pactsafe.pactsafeandroidsdk.models

import kotlinx.serialization.Serializable

@Serializable
data class PSGroup(
    val type: String,
    val renderID: String?,
    val forceScroll: Boolean?,
    //Unused and is currently always false
    val triggered: Boolean?,
    /**
     * Note: Unused For the Android SDK
     * The setting of whether all contracted should be displayed immediately
     * A contract will only be displayed if the signer hasn't accepted the latest version
     */
    val displayAll: Boolean? = false,
    // Group Key
    val key: String,
    // Group ID
    val id: Int,
    // Contract IDs that are part of the group
    val contracts: List<Int>,
    // Contract Version IDs that are part of the group.
    val versions: List<String>,
    // Major version IDs of the contract
    val majorVersions: List<String>,
    // Clickwrap style of the group
    val style: String?,
    // Current setting for whether the form submission should be blocked
    val blockFormSubmission: Boolean,
    // Alert message to be displayed when acceptance is required
    val alertMessage: String,
    // URL of the legal center for the PactSafe site.
    val legalCenterURL: String,
    // Acceptance language that is set within the group's settings.
    val acceptanceLanguage: String,
    // Contracts data where the contract ID is the key.
    val contactData: Map<String, PSContract>,
    // The time (in epoch) of when the group was fetched.
    val renderedTime: Int,
    // The rendered HTML of the clickwrap.
    val contractHTML: String?,
    // Locale of the group
    val locale: String?,
    // The current setting within the group for whether a confirmation should be sent upon acceptance.
    val confirmationEmail: Boolean
) {

    // The contract IDs separated by a`,` and returned as a string.
    var contractIds: String
        get() = contracts.map { it }.joinToString(",")
        set(value) {}

    // The contract versions separated by a `,` and returned as a string.
    var contractVersions: String?
        get() = versions.map { it }.joinToString(",")
        set(value) {}

}
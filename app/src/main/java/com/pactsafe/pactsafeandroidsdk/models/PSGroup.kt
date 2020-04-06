package com.pactsafe.pactsafeandroidsdk.models

import kotlinx.serialization.Serializable

@Serializable
data class PSGroup(
    val type: String,
    val render_id: String?,
    val force_scroll: Boolean?,
    //Unused and is currently always false
    val triggered: Boolean?,
    /**
     * Note: Unused For the Android SDK
     * The setting of whether all contracted should be displayed immediately
     * A contract will only be displayed if the signer hasn't accepted the latest version
     */
    val display_all: Boolean? = false,
    // Group Key
    val key: String,
    // Group ID
    val id: Int,
    // Contract IDs that are part of the group
    val contracts: List<Int>,
    // Contract Version IDs that are part of the group.
    val versions: List<String>,
    // Major version IDs of the contract
    val major_versions: List<String>,
    // Clickwrap style of the group
    val style: String?,
    // Current setting for whether the form submission should be blocked
    val block_form_submission: Boolean,
    // Alert message to be displayed when acceptance is required
    val alert_message: String,
    // URL of the legal center for the PactSafe site.
    val legal_center_url: String,
    // Acceptance language that is set within the group's settings.
    val acceptance_language: String,
    // Contracts data where the contract ID is the key.
    val contract_data: Map<String, PSContract>,
    // The time (in epoch) of when the group was fetched.
    val rendered_time: Int,
    // The rendered HTML of the clickwrap.
    val contract_html: String?,
    // Locale of the group
    val locale: String?,
    // The current setting within the group for whether a confirmation should be sent upon acceptance.
    val confirmation_email: Boolean
) {

    // The contract IDs separated by a`,` and returned as a string.
    var contract_ids: String
        get() = contracts.map { it }.joinToString(",")
        set(value) {}

    // The contract versions separated by a `,` and returned as a string.
    var contract_versions: String?
        get() = versions.map { it }.joinToString(",")
        set(value) {}

}

package com.pactsafe.pactsafeandroidsdk.models

import kotlinx.serialization.Serializable

typealias PSSignerID = String

@Serializable
data class PSSigner(
    val signerId: PSSignerID,
    val customData: PSCustomData = PSCustomData()
)
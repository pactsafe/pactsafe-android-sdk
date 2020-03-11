package com.pactsafe.pactsafeandroidsdk.models

import kotlinx.serialization.Serializable

typealias PSSignerID = String

@Serializable
data class PSSIgner(
    val signerId: PSSignerID,
    val customData: PSCustomData
)
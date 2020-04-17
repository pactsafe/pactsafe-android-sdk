package com.pactsafe.pactsafeandroidsdk.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

typealias PSSignerID = String

@Serializable
@Parcelize
data class PSSigner(
    val signerId: PSSignerID = "",
    val customData: PSCustomData = PSCustomData()
): Parcelable
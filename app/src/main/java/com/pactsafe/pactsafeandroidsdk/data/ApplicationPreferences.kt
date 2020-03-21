package com.pactsafe.pactsafeandroidsdk.data

import com.pactsafe.pactsafeandroidsdk.models.PSGroup


interface ApplicationPreferences {
    var group: PSGroup?
    var siteAccessId: String
    var psGroupKey: String
}
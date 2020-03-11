package com.pactsafe.pactsafeandroidsdk

object PSApp {

    private var siteAccessId: String? = null

    fun init(siteAccessId: String) {
        this.siteAccessId = siteAccessId
    }

    fun preload(groupKey: String) {
        println("WE GOT THIS FAR BUT WE'RE NOT OUT YET")
        
    }
}
package com.pactsafe.demo

import android.os.Bundle
import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import com.pactsafe.pactsafeandroidsdk.ui.PSClickWrapActivity

class LoginActivity : PSClickWrapActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_alert)
    }

    override fun onPreLoaded(psGroup: PSGroup) {
        println("THE LOGIN ACTIVITY HAS GOTTEN THE PRELOAD")
    }

}
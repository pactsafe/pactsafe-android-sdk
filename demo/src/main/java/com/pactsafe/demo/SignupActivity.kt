package com.pactsafe.demo

import android.os.Bundle
import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import com.pactsafe.pactsafeandroidsdk.ui.PSClickWrapActivity

class SignupActivity : PSClickWrapActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    override fun onPreLoaded(psGroup: PSGroup) {
        println("THE SIGNUP ACTIVITY HAS GOTTEN THE PRELOAD")
    }

}
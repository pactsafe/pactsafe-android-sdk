package com.pactsafe.demo

import android.content.Intent
import android.os.Bundle
import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import com.pactsafe.pactsafeandroidsdk.models.PSSigner
import com.pactsafe.pactsafeandroidsdk.ui.PSClickWrapActivity
import kotlinx.android.synthetic.main.activity_login_alert.*

class LoginActivity : PSClickWrapActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_alert)

        edit_username.textChangedListener {
            enableLoginBtn()
        }

        edit_password.textChangedListener {
            enableLoginBtn()
        }

        btn_login.setOnClickListener {
            fetchSignedStatus(edit_username.text.toString())
            it.isEnabled = false
        }

        supportActionBar?.setHomeButtonEnabled(true)
    }

    /** PS METHODS */
    override fun onPreLoaded(psGroup: PSGroup) {
        println("THE LOGIN ACTIVITY HAS GOTTEN THE PRELOAD")
    }

    override fun onContractLinkClicked(title: String, url: String) {
        println("CONTRACT LINK: $url")
    }

    override fun onAcceptanceComplete(checked: Boolean) {
        println("IS CHECKED : $checked")
        btn_login.isEnabled = checked
    }

    override fun onSendAgreedComplete(downloadUrl: String) {
        navigateToHome()
    }

    override fun onSignedStatusFetched(status: Map<String, Boolean>) {

        val updateSignedStatus = status.values.any { !it }

        val signer = PSSigner(edit_username.text.toString())

        if (updateSignedStatus) {
            showTermsIntercept(ALERT_TYPE, status, signer)
        } else {
            navigateToHome()
        }
    }
    /**/

    private fun navigateToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun enableLoginBtn() {
        btn_login.isEnabled = edit_username.isValidEmail() && edit_password.isValidPassword()
    }
}
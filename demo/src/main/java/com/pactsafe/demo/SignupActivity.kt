package com.pactsafe.demo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.pactsafe.pactsafeandroidsdk.PSApp
import com.pactsafe.pactsafeandroidsdk.models.PSCustomData
import com.pactsafe.pactsafeandroidsdk.models.PSGroup
import com.pactsafe.pactsafeandroidsdk.models.PSSigner
import com.pactsafe.pactsafeandroidsdk.ui.PSClickWrapActivity
import com.pactsafe.pactsafeandroidsdk.util.PSResult
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : PSClickWrapActivity() {

    private var enableLoginButton = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        edit_first_name.textChangedListener {
            enableLoginBtn()
        }
        edit_last_name.textChangedListener {
            enableLoginBtn()
        }
        edit_email.textChangedListener {
            enableLoginBtn()
        }
        edit_password.textChangedListener {
            enableLoginBtn()
        }
        edit_re_password.textChangedListener {
            enableLoginBtn()
        }

        btn_signup.setOnClickListener {

            it.isEnabled = false

            val signer = PSSigner(
                edit_email.text.toString(),
                PSCustomData(edit_first_name.text.toString(), edit_last_name.text.toString())
            )

            sendAgreed(signer)
        }
    }

    /**
     * PS METHODS
     */
    override fun onPreLoaded(psGroup: PSGroup) {
        println("ON PRELOADED")
    }

    override fun onContractLinkClicked(title: String, url: String) {
        Toast.makeText(this, "The link for $title has been clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onAcceptanceComplete(checked: Boolean) {
        enableLoginButton = checked
        enableLoginBtn()
    }

    override fun onSendAgreedComplete(downloadUrl: String) {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun onSignedStatusFetched(status: Map<String, Boolean>) {
        TODO("Not yet implemented")
    }
    /**/


    private fun enableLoginBtn() {
        btn_signup.isEnabled = edit_first_name.text?.isNotEmpty() ?: false
                && edit_last_name.text?.isNotEmpty() ?: false
                && edit_password.text?.length == edit_re_password.text?.length
                && edit_password.isValidPassword()
                && edit_re_password.isValidPassword()
                && edit_email.isValidEmail()
                && enableLoginButton
    }

}
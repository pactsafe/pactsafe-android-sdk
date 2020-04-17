package com.pactsafe.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pactsafe.pactsafeandroidsdk.ui.PSClickWrapActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_sign_up.setOnClickListener {
            this.startActivity(Intent(this, SignupActivity::class.java))
        }

        btn_login_with_alert.setOnClickListener {
            this.startActivity(
                PSClickWrapActivity.create(
                    this,
                    LoginActivity::class.java,
                    PSClickWrapActivity.ClickWrapType.ALERT
                )
            )
        }

        btn_login_with_checkbox.setOnClickListener {
            this.startActivity(
                PSClickWrapActivity.create(
                    this,
                    LoginActivity::class.java,
                    PSClickWrapActivity.ClickWrapType.CHECKBOX
                )
            )
        }
    }
}

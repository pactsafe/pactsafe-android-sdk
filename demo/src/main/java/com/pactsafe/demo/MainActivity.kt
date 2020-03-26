package com.pactsafe.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO Determine if it makes sense to make checkbox/alert an default arg
        btnSignUp.setOnClickListener {
            this.startActivity(Intent(this, SignupActivity::class.java))
        }

        btnLoginWithAlert.setOnClickListener {
            this.startActivity(Intent(this, LoginActivity::class.java))
        }

        btnLoginWithCheckbox.setOnClickListener {
            this.startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}

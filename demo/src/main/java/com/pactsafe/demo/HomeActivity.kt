package com.pactsafe.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pactsafe.pactsafeandroidsdk.PSApp
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        txt_logout_btn.setOnClickListener {
            PSApp.clearPSApp()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
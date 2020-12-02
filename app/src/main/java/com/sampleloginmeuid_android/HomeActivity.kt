package com.sampleloginmeuid_android

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        intent?.data?.apply {
            val code = getQueryParameter("code")
            val codeVerifier = getQueryParameter("code_verifier")

            // Handle parameters here...
            findViewById<TextView>(R.id.code).text = "code = $code"
            findViewById<TextView>(R.id.codeVerifier).text = "code_verifier = $codeVerifier"
        }
    }
}
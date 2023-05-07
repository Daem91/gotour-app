package com.dapm.gotour.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.dapm.gotour.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val username = intent.getStringExtra("usuario")

        val myTextView = findViewById<TextView>(R.id.welcome)
        myTextView.text = "te amo " + username
    }


}
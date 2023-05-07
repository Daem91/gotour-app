package com.dapm.gotour.singin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dapm.gotour.R

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val fragment = LoginFragment()
        supportFragmentManager.beginTransaction().add(R.id.main_container, fragment).commit()
    }
}
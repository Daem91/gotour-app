package com.dapm.gotour

import android.app.backup.SharedPreferencesBackupHelper
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.config.DataBaseInitializer
import com.dapm.gotour.singin.StartActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // inicializar data base
        val dataBaseHandler  = DataBaseHandler(this)

        val dataBaseInitializer = DataBaseInitializer()
        with(dataBaseInitializer) {
            inicializarCiudades(dataBaseHandler)
        }
        dataBaseInitializer.inicializarDestinos(this, dataBaseHandler, 1)
        val miBoton = findViewById<Button>(R.id.start_button)

        miBoton.setOnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        }


    }

    /*
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val dataBaseInitializer = DataBaseInitializer()
                    val dataBaseHandler  = DataBaseHandler(this)
                    dataBaseInitializer.inicializarDestinos(this, dataBaseHandler, 1)
                } else {
                    Toast.makeText(this, "Permiso de almacenamiento externo denegado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    */


}
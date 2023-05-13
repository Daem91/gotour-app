package com.dapm.gotour.home

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.Destino

import com.dapm.gotour.databinding.ActivityDetalleBinding
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.io.File

private val PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1

class DetalleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleBinding
    private lateinit var destino: Destino


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataBaseHandler = DataBaseHandler(this)

        val id_destino = intent.getIntExtra("id_destino", -1)
        destino = dataBaseHandler.obtenerDestinoPorId(id_destino)!!

        val imageView = findViewById<ImageView>(R.id.imagen_destino)
        Glide.with(this).load(destino.imagen)
            .transform(RoundedCornersTransformation(30, 0))
            .into(imageView)

        val nombreDestino = findViewById<TextView>(R.id.destino_nombre)
        nombreDestino.text = destino.nombre

        val ubicacion = findViewById<TextView>(R.id.ubicacion)
        ubicacion.text = destino.ubicacion

        val descripcion = findViewById<TextView>(R.id.descripcion)
        descripcion.text = destino.descripcion
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            descripcion.justificationMode  = JUSTIFICATION_MODE_INTER_WORD
        }



    }
}
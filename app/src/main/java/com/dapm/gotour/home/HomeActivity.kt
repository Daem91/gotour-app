package com.dapm.gotour.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.config.DataBaseInitializer
import com.dapm.gotour.database.model.CiudadAdapter
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ciudadAdapter: CiudadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.ciudades_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val username = intent.getStringExtra("usuario")

        val dataBaseHandler = DataBaseHandler(this)
        val ciudades = dataBaseHandler.listarCiudades()

        ciudadAdapter = CiudadAdapter(ciudades)
        recyclerView.adapter = ciudadAdapter




    }


}
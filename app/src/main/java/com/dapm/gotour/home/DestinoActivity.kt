package com.dapm.gotour.home


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.config.DataBaseInitializer
import com.dapm.gotour.database.model.Ciudad
import com.dapm.gotour.database.model.DestinoAdapter
import com.dapm.gotour.databinding.ActivityDestinoBinding


class DestinoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDestinoBinding
    private lateinit var ciudad: Ciudad
    private lateinit var recyclerView: RecyclerView
    private lateinit var destinoAdapter: DestinoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destino)

        binding = ActivityDestinoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MUY IMPORTANTE NO OLVIDAAAAAAAAR
        recyclerView = findViewById(R.id.destinos_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dataBaseHandler = DataBaseHandler(this)

        val id_ciudad = intent.getIntExtra("id_ciudad", -1)
        ciudad = dataBaseHandler.obtenerCiudadPorId(id_ciudad)!!

        val destinos = dataBaseHandler.obtenerDestinos(id_ciudad)
        val myTextView = findViewById<TextView>(R.id.destino_ciudad)
        myTextView.text = "Lugares Turísticos de: " + ciudad.nombre

        destinoAdapter = DestinoAdapter(destinos)
        recyclerView.adapter = destinoAdapter

    }
}
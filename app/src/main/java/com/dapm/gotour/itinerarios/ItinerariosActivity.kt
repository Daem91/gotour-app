package com.dapm.gotour.itinerarios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.Itinerario
import com.dapm.gotour.database.model.ItinerarioAdapter
import com.dapm.gotour.databinding.ActivityHomeBinding

import com.dapm.gotour.databinding.ActivityItinerariosBinding

class ItinerariosActivity : AppCompatActivity() {



    private lateinit var recyclerView: RecyclerView

    private lateinit var binding: ActivityItinerariosBinding
    private var adapter:ItinerarioAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItinerariosBinding.inflate(layoutInflater) // Inicializa el objeto de enlace (binding)
        val view = binding.root
        setContentView(view)

        val btnCrearItinerario: Button = binding.btnCrearItinerario
        btnCrearItinerario.setOnClickListener {
            val intent = Intent(this, ActivityCreateItinerary::class.java)
            startActivity(intent)
        }

        iniRecyclerView()
    }

    private fun obtenerItinerarios(): ArrayList<Itinerario> {
        val dbHandler = DataBaseHandler(this)
        return dbHandler.obtenerItinerarios()
    }

    private fun iniRecyclerView() {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ItinerarioAdapter()
        recyclerView.adapter = adapter

        // Obtener los itinerarios y actualizar el adaptador
        val itinerarios = obtenerItinerarios()
        adapter?.let {
            it.addItems(itinerarios)
        }
    }




}
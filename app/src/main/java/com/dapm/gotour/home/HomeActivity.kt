package com.dapm.gotour.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.config.DataBaseInitializer
import com.dapm.gotour.database.model.Ciudad
import com.dapm.gotour.database.model.CiudadAdapter
import com.dapm.gotour.databinding.ActivityHomeBinding
import com.dapm.gotour.itinerarios.ItinerariosActivity
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.util.*
import kotlin.collections.ArrayList
class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ciudadAdapter: CiudadAdapter
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.ciudadesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dataBaseHandler = DataBaseHandler(this)
        val ciudades = dataBaseHandler.listarCiudades()

        ciudadAdapter = CiudadAdapter(ciudades)
        recyclerView.adapter = ciudadAdapter

        binding.barraBusqueda.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                filtrarCiudades(query)
                return true
            }

        })

        val btnItinerarios: Button = binding.itinerarios
        btnItinerarios.setOnClickListener {
            val intent = Intent(this, ItinerariosActivity::class.java)
            startActivity(intent)
        }
    }

    fun filtrarCiudades(query: String?) {
        val dataBaseHandler = DataBaseHandler(this)
        val ciudades = dataBaseHandler.listarCiudades()

        if (query != null) {
            val listaFiltrada = ArrayList<Ciudad>()
            for (i in ciudades) {
                if (i.nombre.lowercase(Locale.ROOT).contains(query)) {
                    listaFiltrada.add(i)
                }
            }

            if (listaFiltrada.isEmpty()) {
                Toast.makeText(this, "No se encontraron datos", Toast.LENGTH_SHORT).show()
            } else {
                ciudadAdapter.setListaFiltrada(listaFiltrada)
            }
        }
    }
}

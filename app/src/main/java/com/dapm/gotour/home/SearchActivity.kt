package com.dapm.gotour.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.Destino
import com.dapm.gotour.database.model.DestinoAdapter
import com.dapm.gotour.databinding.ActivitySearchBinding
import com.dapm.gotour.itinerarios.ItinerariosActivity
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var destinoAdapter: DestinoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.selectedItemId = R.id.search
        bottomNavigationView.setOnItemSelectedListener { menuItem ->

            menuItem.isChecked = true

            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.search -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.itinerarios -> {
                    val intent = Intent(this, ItinerariosActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.favoritos -> {
                    val intent = Intent(this, FavoritosActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                else -> false
            }
        }

        val tags = listOf("Histórico","Cultural","Arquitectura","Patrimonio","Museo","Punto de encuentro","Naturaleza","Aventura","Paisajes","Trekking","Monumento","Construcción tradicional","Ecoturismo","Vida silvestre","Selva amazónica","Recreación","Parque temático","Animales","Arte","Vista al mar","Paseo costero","Actividades al aire libre","Restaurantes y tiendas","Playa","Surf","Relax","Atardeceres")
//        generateTagViews(tags)

        recyclerView = findViewById(R.id.destinos_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dataBaseHandler = DataBaseHandler(this)

        val destinos = dataBaseHandler.obtenerTodosLosDestinos()

        destinoAdapter = DestinoAdapter(destinos)
        recyclerView.adapter = destinoAdapter

        binding.barraBusqueda.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                filtrarDestinos(query)
                return true
            }

        })

    }

    fun filtrarDestinos(query: String?) {

        val dataBaseHandler = DataBaseHandler(this)

        val destinos = dataBaseHandler.obtenerTodosLosDestinos()

        if (query != null) {
            val listaFiltrada = ArrayList<Destino>()
            for (i in destinos) {
                if (i.nombre.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))){
                    listaFiltrada.add(i)
                }
            }

            if (listaFiltrada.isEmpty()) {
                binding.resultadosVisibilidad.visibility = View.VISIBLE
                destinoAdapter.setListaFiltrada(listaFiltrada)
            } else {
                binding.resultadosVisibilidad.visibility = View.GONE
                destinoAdapter.setListaFiltrada(listaFiltrada)
            }
        }

    }
}
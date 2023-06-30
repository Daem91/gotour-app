package com.dapm.gotour.home


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.config.DataBaseInitializer
import com.dapm.gotour.database.model.Ciudad
import com.dapm.gotour.database.model.Destino
import com.dapm.gotour.database.model.DestinoAdapter
import com.dapm.gotour.databinding.ActivityDestinoBinding
import com.dapm.gotour.itinerarios.ItinerariosActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList


class DestinoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDestinoBinding
    private lateinit var ciudad: Ciudad
    private lateinit var recyclerView: RecyclerView
    private lateinit var destinoAdapter: DestinoAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destino)

        binding = ActivityDestinoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
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

        // MUY IMPORTANTE
        recyclerView = findViewById(R.id.destinos_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dataBaseHandler = DataBaseHandler(this)

        val id_ciudad = intent.getIntExtra("id_ciudad", -1)
        ciudad = dataBaseHandler.obtenerCiudadPorId(id_ciudad)!!

        val destinos = dataBaseHandler.obtenerDestinos(id_ciudad)
        println(destinos)
        val myTextView = findViewById<TextView>(R.id.destino_ciudad)
        myTextView.text = "Lugares Turísticos de: " + ciudad.nombre

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

        val id_ciudad = intent.getIntExtra("id_ciudad", -1)
        val destinos = dataBaseHandler.obtenerDestinos(id_ciudad)

        if (query != null) {
            val listaFiltrada = ArrayList<Destino>()
            for (i in destinos) {
                if (i.nombre.lowercase(Locale.ROOT).contains(query)){
                    listaFiltrada.add(i)
                }
            }

            if (listaFiltrada.isEmpty()) {
                Toast.makeText(this, "No se encontraron datos", Toast.LENGTH_SHORT).show()
            } else {
                destinoAdapter.setListaFiltrada(listaFiltrada)
            }
        }

    }

}
package com.dapm.gotour.itinerarios

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.Itinerario
import com.dapm.gotour.database.model.ItinerarioAdapter
import com.dapm.gotour.database.model.Usuario
import com.dapm.gotour.databinding.ActivityHomeBinding

import com.dapm.gotour.databinding.ActivityItinerariosBinding
import com.dapm.gotour.home.FavoritosActivity
import com.dapm.gotour.home.HomeActivity
import com.dapm.gotour.home.SearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ItinerariosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var binding: ActivityItinerariosBinding
    private var adapter:ItinerarioAdapter?=null

    private val username = Usuario.username

    companion object {
        private const val REQUEST_CODE_EDITAR_ITINERARIO = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItinerariosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.selectedItemId = R.id.itinerarios
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

        val btnCrearItinerario: Button = binding.btnCrearItinerario
        btnCrearItinerario.setOnClickListener {
            val intent = Intent(this, ActivityCreateItinerary::class.java)
            startActivity(intent)
            finish()
        }

        iniRecyclerView()

    }

    private fun obtenerItinerarios(): ArrayList<Itinerario> {
        val dbHandler = DataBaseHandler(this)
        return dbHandler.obtenerItinerariosPorUsername(username)

    }

    private fun iniRecyclerView() {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ItinerarioAdapter()
        recyclerView.adapter = adapter

        // Obtener los itinerarios y actualizar el adaptador
        val itinerarios = obtenerItinerarios()
        if (itinerarios.isEmpty()) {
            binding.resultadosVisibilidad.visibility = View.VISIBLE
        } else {
            binding.resultadosVisibilidad.visibility = View.GONE
        }
        adapter?.let {
            it.addItems(itinerarios)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EDITAR_ITINERARIO && resultCode == Activity.RESULT_OK) {
            val idItinerario = data?.getIntExtra("idItinerario", -1)
            val fechaInicio = data?.getStringExtra("fechaInicio")
            val nombre = data?.getStringExtra("nombre")
            val fechaFin = data?.getStringExtra("fechaFin")
            if (idItinerario != -1 && !fechaInicio.isNullOrEmpty()&& !fechaFin.isNullOrEmpty()&& !nombre.isNullOrEmpty()) {
                val dbHandler = DataBaseHandler(this)
                val nuevaLista = dbHandler.obtenerItinerariosPorUsername(username)
                if (nuevaLista.isEmpty()) {
                    binding.resultadosVisibilidad.visibility = View.VISIBLE
                } else {
                    binding.resultadosVisibilidad.visibility = View.GONE
                }
                adapter?.actualizarDatosItinerario(nuevaLista)
            }
        }
    }

}
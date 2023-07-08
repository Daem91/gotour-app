package com.dapm.gotour.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.*
import com.dapm.gotour.databinding.ActivityItinerariosBinding
import com.dapm.gotour.databinding.ActivityResenasBinding
import com.dapm.gotour.itinerarios.ItinerariosActivity
import com.dapm.gotour.resenas.EditarResenaActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ResenasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityResenasBinding
    private var adapter: ResenaAdapter?=null
    var ideazoDestino: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResenasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recyclerView = findViewById(R.id.resenas_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val nameDestino = intent.getStringExtra("name_destino")
        ideazoDestino = intent.getIntExtra("id_destino",-1)
        binding.resenasTitle.text = nameDestino

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.selectedItemId = R.id.home
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

        fun getResenas(): ArrayList<Resena> {
            val destino = ideazoDestino
            val dbHandler = DataBaseHandler(this)
            return dbHandler.obtenerResenasPorIdDestino(destino)

        }

        val resenas = getResenas()
        if (resenas.isEmpty()) {
            binding.resenasVisibilidad.visibility = View.VISIBLE

        } else {
            binding.resenasVisibilidad.visibility = View.GONE
        }
        adapter = ResenaAdapter( resenas )
        recyclerView.adapter = adapter
        adapter?.let {
            it.addItems(resenas)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EditarResenaActivity.REQUEST_CODE_EDITAR_RESENA && resultCode == Activity.RESULT_OK) {
            val idResena = data?.getIntExtra("idResena", -1)
            val idDestino = data?.getIntExtra("idDestino", -1)
            val descripcion = data?.getStringExtra("descripcion")
            val username = data?.getStringExtra("username")
            val fecha = data?.getStringExtra("fecha")
            if (idResena != -1 && idDestino != -1 && !fecha.isNullOrEmpty()&& !descripcion.isNullOrEmpty()&& !username.isNullOrEmpty()) {
                val dbHandler = DataBaseHandler(this)
                val nuevaLista = dbHandler.obtenerResenasPorIdDestino(idDestino!!)
                runOnUiThread {
                    adapter?.actualizarDatos(nuevaLista)
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }


}
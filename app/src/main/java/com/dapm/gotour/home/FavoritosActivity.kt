package com.dapm.gotour.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.DestinoAdapter
import com.dapm.gotour.database.model.Usuario
import com.dapm.gotour.databinding.ActivityDestinoBinding
import com.dapm.gotour.databinding.ActivityFavoritosBinding
import com.dapm.gotour.itinerarios.ItinerariosActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavoritosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritosBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var destinoAdapter: DestinoAdapter
    private val username = Usuario.username


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.selectedItemId = R.id.favoritos
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

        val dataBaseHandler = DataBaseHandler(this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val destinos = dataBaseHandler.obtenerFavoritosPorUsername(username)
        destinoAdapter = DestinoAdapter(destinos)
        recyclerView.adapter = destinoAdapter

    }
}
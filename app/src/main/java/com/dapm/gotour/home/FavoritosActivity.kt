package com.dapm.gotour.home

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

class FavoritosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritosBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var destinoAdapter: DestinoAdapter
    private val username = Usuario.username


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataBaseHandler = DataBaseHandler(this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val destinos = dataBaseHandler.obtenerFavoritosPorUsername(username)
        destinoAdapter = DestinoAdapter(destinos)
        recyclerView.adapter = destinoAdapter

    }
}
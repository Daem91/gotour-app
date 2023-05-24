package com.dapm.gotour.itinerarios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.RegistroDestinoAdapter

class VerItinerarioActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RegistroDestinoAdapter
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_itinerario)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val idItinerario = intent.getIntExtra("id_itinerario",-1)
        val nameItinerario = intent.getStringExtra("name_itinerario")

        val txt = findViewById<TextView>(R.id.tvTitle)
        txt.text = nameItinerario

        val dataBaseHandler=DataBaseHandler(this)
        val nombresDestino=dataBaseHandler.obtenerNombresDestinoPorRegistroItinerario(idItinerario)
        adapter = RegistroDestinoAdapter(nombresDestino) 
        recyclerView.adapter = adapter
    }
}

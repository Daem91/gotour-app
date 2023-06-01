package com.dapm.gotour.itinerarios

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.RegistroDestinoAdapter
import com.dapm.gotour.databinding.ActivityVerItinerarioBinding

class VerItinerarioActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RegistroDestinoAdapter
    private lateinit var binding: ActivityVerItinerarioBinding

    companion object {
        private const val REQUEST_CODE_EDITAR_REGISTRO = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_itinerario)

        binding = ActivityVerItinerarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val idItinerario = intent.getIntExtra("id_itinerario",-1)
        val nameItinerario = intent.getStringExtra("name_itinerario")

        val txt = findViewById<TextView>(R.id.tvTitle)
        txt.text = nameItinerario

        val fechaI = intent.getStringExtra("fechaI")

        val fechaInicio = findViewById<TextView>(R.id.fechaI)
        fechaInicio.text = fechaI

        val fechaF = intent.getStringExtra("fechaF")

        val fechaFinal = findViewById<TextView>(R.id.fechaF)
        fechaFinal.text = fechaF



        val dataBaseHandler=DataBaseHandler(this)
        val destinosRegistrados = dataBaseHandler.obtenerDestinosRegistradosPorItinerario(idItinerario)
        adapter = RegistroDestinoAdapter(destinosRegistrados, idItinerario)
        recyclerView.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EDITAR_REGISTRO && resultCode == Activity.RESULT_OK) {
            val idRegistroActualizado = data?.getIntExtra("idRegistro", -1)
            val idItinerarioActualizado = data?.getIntExtra("idItinerario", -1)!!
            val fechaActualizada = data?.getStringExtra("fechaActualizada")
            if (idRegistroActualizado != -1 && !fechaActualizada.isNullOrEmpty()) {
                val dbHandler = DataBaseHandler(this)
                val nuevaLista = dbHandler.obtenerDestinosRegistradosPorItinerario(idItinerarioActualizado)
                adapter.actualizarDatos(nuevaLista)
            }
        }
    }
}

package com.dapm.gotour.resenas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dapm.gotour.R.*
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.Itinerario
import com.dapm.gotour.database.model.Resena
import com.dapm.gotour.database.model.Usuario
import com.dapm.gotour.home.ResenasActivity
import com.dapm.gotour.itinerarios.ItinerariosActivity
import java.util.*

class CrearResenaActivity : AppCompatActivity() {

    private lateinit var dbHandler: DataBaseHandler
    private lateinit var etNombre: EditText
    var ideazoDestino: Int = -1
    lateinit var nombrezazoDestino: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_crear_resena)

        val btnCancel = findViewById<Button>(id.cancelar_crear)
        etNombre = findViewById(id.text_resena_input)
        btnCancel.setOnClickListener{ finish() }

        dbHandler = DataBaseHandler(this)

        val nameDestino = intent.getStringExtra("name_destino")
        val nombreDestino = findViewById<TextView>(id.destino_nombre)
        nombreDestino.text = nameDestino
        ideazoDestino = intent.getIntExtra("id_destino",-1)
        nombrezazoDestino = nameDestino!!

        val btnCrear = findViewById<Button>(id.aceptar_crear)
        btnCrear.setOnClickListener {
            val resena = etNombre.text.toString()
            if (resena.isNotEmpty()) {
                crearResena()
                etNombre.text.clear()
                finish()
            } else {
                Toast.makeText(this, "Tienes que ingresar algo.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun crearResena() {
        val nombre = etNombre.text.toString()
        val fecha = Date().toString()
        val usernameUsuario = Usuario.username
        val idDestino = ideazoDestino
        val nuevaResena = Resena(null, nombre, idDestino, usernameUsuario, fecha)

        dbHandler.crearResena(nuevaResena)

    }

}
package com.dapm.gotour.itinerarios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.Destino
import com.dapm.gotour.database.model.Itinerario
import com.dapm.gotour.database.model.RegistroDestino
import com.dapm.gotour.database.model.Usuario
import com.dapm.gotour.databinding.ActivityAgregarDestinoBinding

class AgregarDestinoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarDestinoBinding
    private lateinit var dbHandler: DataBaseHandler

    private val username = Usuario.username

    //private lateinit var destino: Destino

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_destino)
        binding = ActivityAgregarDestinoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHandler = DataBaseHandler(this)

        val id_destino = intent.getIntExtra("id_destino",-1)
        val nameDestino = intent.getStringExtra("name_destino")
        //destino = dbHandler.obtenerDestinoPorId(id_destino)!!

        //val nombreDestino = findViewById<TextView>(R.id.destino_nombre)
        //nombreDestino.text = nameDestino

        println(username)
        val itinerarios = dbHandler.obtenerItinerariosPorUsername(username)
        val nombresItinerarios = itinerarios.map { it.nombre }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresItinerarios)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.apply {
            destinoNombre.text = nameDestino
            spinnerItinerarios.adapter = adapter

            if (itinerarios.isEmpty()){
                spinnerItinerarios.isEnabled = false
                emptyItinerarios.text= "No hay itinerarios registrados"
                aceptarAgregar.isEnabled = false
            } else{

                aceptarAgregar.setOnClickListener{
                    val nombreItinerarioSeleccionado = binding.spinnerItinerarios.selectedItem as String

                    val itinerarioSeleccionado = itinerarios.find { it.nombre == nombreItinerarioSeleccionado }

                    itinerarioSeleccionado?.let {
                        val idItinerarioSeleccionado = it.id_itinerario ?:1
                        agregarDestinoItinerario(idItinerarioSeleccionado, id_destino)


                    }



                    //val itinerario = Intent(this, ItinerarioActivity::class.java)
                    //startActivity(itinerario)
                }
            }
            cancelarAgregar.setOnClickListener{
                finish()
            }
        }
    }
    private fun mostrarMensajeExito(){
        Toast.makeText(this,"Destino añadido con éxito.",Toast.LENGTH_SHORT).show()
    }

    private fun agregarDestinoItinerario(idItinerario: Int, idDestino: Int){
        val registro = RegistroDestino(null, idDestino,idItinerario )
        dbHandler.crearRegistro(registro)
        Log.e("Registro", "ID Registro: ${registro.id_registro}, ID Itinerario: ${registro.id_itinerario}, ID Destino: ${registro.id_destino}")
        mostrarMensajeExito()

        val nombresDestino = dbHandler.obtenerNombresDestinoPorRegistroItinerario(idItinerario)
        for (nombreDestino in nombresDestino) {
            Log.d("Destino", nombreDestino)
        }

    }






}
package com.dapm.gotour.itinerarios

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.Destino
import com.dapm.gotour.database.model.Itinerario
import com.dapm.gotour.database.model.RegistroDestino
import com.dapm.gotour.database.model.Usuario
import com.dapm.gotour.databinding.ActivityAgregarDestinoBinding
import java.text.SimpleDateFormat
import java.util.*

class AgregarDestinoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarDestinoBinding
    private lateinit var dbHandler: DataBaseHandler

    private lateinit var etFecha: EditText

    private val calendarInicio: Calendar = Calendar.getInstance()

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
        etFecha= findViewById(R.id.etFecha)
        etFecha.setOnClickListener { showDatePickerDialogInicio() }

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

                    if (etFecha.text.isEmpty()) {
                        mostrarMensajeFallido()
                    } else {
                        val nombreItinerarioSeleccionado = binding.spinnerItinerarios.selectedItem as String

                        val itinerarioSeleccionado = itinerarios.find { it.nombre == nombreItinerarioSeleccionado }

                        itinerarioSeleccionado?.let {
                            val idItinerarioSeleccionado = it.id_itinerario ?:1
                            Log.e("Fecha" , etFecha.text.toString())

                            agregarDestinoItinerario(idItinerarioSeleccionado, id_destino, etFecha.text.toString())

                            val intent = Intent(this.root.context, ItinerariosActivity::class.java)
                            startActivity(intent)
                            finish()

                        }
                    }

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

    private fun mostrarMensajeFallido(){
        Toast.makeText(this,"No puedes dejar ningún campo vacío.",Toast.LENGTH_SHORT).show()
    }

    private fun agregarDestinoItinerario(idItinerario: Int, idDestino: Int, fecha: String){
        val registro = RegistroDestino(null, idDestino,idItinerario, fecha )
        dbHandler.crearRegistro(registro)
        Log.e("Registro", "ID Registro: ${registro.id_registro}, ID Itinerario: ${registro.id_itinerario}, ID Destino: ${registro.id_destino}")
        mostrarMensajeExito()

        val destinosRegistrados = dbHandler.obtenerDestinosRegistradosPorItinerario(idItinerario)
        for (destinoRegistrado in destinosRegistrados) {
            val nombreDestino = destinoRegistrado.first
            val fechaRegistro = destinoRegistrado.second
            Log.d("Destino", "Nombre: $nombreDestino, Fecha: $fechaRegistro")
        }

    }

    private fun showDatePickerDialogInicio() {
        val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            calendarInicio.set(Calendar.YEAR, year)
            calendarInicio.set(Calendar.MONTH, month)
            calendarInicio.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateFechaInicioEditText()
        }, calendarInicio.get(Calendar.YEAR), calendarInicio.get(Calendar.MONTH), calendarInicio.get(
            Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
    }
    private fun updateFechaInicioEditText() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaInicioText = dateFormat.format(calendarInicio.time)
        etFecha.setText(fechaInicioText)
    }






}
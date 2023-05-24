package com.dapm.gotour.itinerarios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dapm.gotour.R

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.config.TABLE_ITINERARIO
import com.dapm.gotour.database.model.Itinerario
import com.dapm.gotour.database.model.Usuario
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ActivityCreateItinerary : AppCompatActivity() {
    private lateinit var etFechaInicio: EditText
    private lateinit var etFechaFin: EditText
    private lateinit var etNombre: EditText

    private val calendarInicio: Calendar = Calendar.getInstance()
    private val calendarFin: Calendar = Calendar.getInstance()



    private lateinit var btnGuardarItinerario: Button
    private lateinit var btnCancel: Button


    private lateinit var dbHandler: DataBaseHandler



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_itinerary)

        etNombre = findViewById(R.id.etNombre)

        etFechaInicio = findViewById(R.id.etFechaInicio)
        etFechaInicio.setOnClickListener { showDatePickerDialogInicio() }

        etFechaFin = findViewById(R.id.etFechaFin)
        etFechaFin.setOnClickListener { showDatePickerDialogFin() }



        btnGuardarItinerario = findViewById(R.id.btnCrearItinerario)
        btnCancel = findViewById(R.id.btnCancelar)

        btnCancel.setOnClickListener{ finish() }

        btnGuardarItinerario.setOnClickListener {
            val nombre = etNombre.text.toString()
            val fechaInicio = etFechaInicio.text.toString()
            val fechaFin = etFechaFin.text.toString()

            if (nombre.isNotEmpty() && fechaInicio.isNotEmpty() && fechaFin.isNotEmpty()) {
                guardarItinerario()
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            }

        }

        dbHandler = DataBaseHandler(this)


    }

    private fun guardarItinerario() {
        val nombre = etNombre.text.toString()
        val fechaInicio = etFechaInicio.text.toString()
        val fechaFin = etFechaFin.text.toString()
        val usernameUsuario = Usuario.username

        val itinerario = Itinerario(null, nombre, fechaInicio, fechaFin, usernameUsuario)

        dbHandler.crearItinerario(itinerario)
        mostrarMensajeExito()
        limpiarCampos()

        val itinerarios = Intent(this, ItinerariosActivity::class.java)
        startActivity(itinerarios)
        finish()
    }



    private fun mostrarMensajeExito() {
        Toast.makeText(this, "Itinerario creado exitosamente", Toast.LENGTH_SHORT).show()
    }

    private fun limpiarCampos() {
        etNombre.text.clear()
        etFechaInicio.text.clear()
        etFechaFin.text.clear()
    }

    private fun parseFecha(fechaString: String): Date? {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return try {
            format.parse(fechaString)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }



    private fun showDatePickerDialogInicio() {
        val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            calendarInicio.set(Calendar.YEAR, year)
            calendarInicio.set(Calendar.MONTH, month)
            calendarInicio.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateFechaInicioEditText()
        }, calendarInicio.get(Calendar.YEAR), calendarInicio.get(Calendar.MONTH), calendarInicio.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
    }

    private fun showDatePickerDialogFin() {
        val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            calendarFin.set(Calendar.YEAR, year)
            calendarFin.set(Calendar.MONTH, month)
            calendarFin.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateFechaFinEditText()
        }, calendarFin.get(Calendar.YEAR), calendarFin.get(Calendar.MONTH), calendarFin.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
    }

    private fun updateFechaInicioEditText() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaInicioText = dateFormat.format(calendarInicio.time)
        etFechaInicio.setText(fechaInicioText)
    }

    private fun updateFechaFinEditText() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaFinText = dateFormat.format(calendarFin.time)
        etFechaFin.setText(fechaFinText)
    }
}

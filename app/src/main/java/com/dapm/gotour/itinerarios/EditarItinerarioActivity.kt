package com.dapm.gotour.itinerarios

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.Itinerario
import com.dapm.gotour.databinding.ActivityEditarItinerarioBinding
import com.dapm.gotour.databinding.ActivityItinerariosBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

class EditarItinerarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarItinerarioBinding

    private lateinit var etNombre: EditText
    private lateinit var etFechaInicio: EditText
    private lateinit var etFechaFin: EditText
    private lateinit var btnCancelar: Button
    private lateinit var btnGuardar: Button
    private val calendarInicio: Calendar = Calendar.getInstance()
    private val calendarFin: Calendar = Calendar.getInstance()
    private lateinit var dbHandler: DataBaseHandler


    companion object {
        private const val REQUEST_CODE_EDITAR_RESENA = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_itinerario)

        binding = ActivityEditarItinerarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHandler = DataBaseHandler(this)
        etNombre = findViewById(R.id.etNombre)
        etFechaInicio = findViewById(R.id.etFechaInicio)
        etFechaFin = findViewById(R.id.etFechaFin)
        btnCancelar = findViewById(R.id.btnCancelar)
        btnGuardar = findViewById(R.id.btnGuardar)


        val nombreItinerario = intent.getStringExtra("name_itinerario")
        val fechaInicio = intent.getStringExtra("fechaI")
        val fechaFin = intent.getStringExtra("fechaF")

        etNombre.setText(nombreItinerario)
        etFechaInicio.setText(fechaInicio)
        etFechaInicio.setOnClickListener { showDatePickerDialogInicio() }


        etFechaFin.setText(fechaFin)
        etFechaFin.setText(fechaFin)
        etFechaFin.setOnClickListener { showDatePickerDialogFin() }

        binding.apply {

            btnCancelar.setOnClickListener {
                finish()
            }

            btnGuardar.setOnClickListener {

                if (etNombre.text.isEmpty()) {
                    mostrarMensajeFallido()
                } else {
                    val nombre = etNombre.text.toString()
                    val fechaInicio = etFechaInicio.text.toString()
                    val fechaFin = etFechaFin.text.toString()
                    val idItinerario = intent.getIntExtra("id_itinerario", -1)

                    val itinerario = Itinerario(id_itinerario = idItinerario, nombre = nombre,
                        fecha_inicio = fechaInicio, fecha_fin = fechaFin)

                    dbHandler.updateItinerario(itinerario)
                    mostrarMensajeExito()
                    val intent = Intent()
                    intent.putExtra("idItinerario", itinerario.id_itinerario)
                    intent.putExtra("nombre", itinerario.nombre)
                    intent.putExtra("fechaInicio", itinerario.fecha_inicio)
                    intent.putExtra("fechaFin", itinerario.fecha_fin)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }

            }

        }

    }

    private fun mostrarMensajeFallido(){
        Toast.makeText(this,"No puedes dejar ningún campo vacío.", Toast.LENGTH_SHORT).show()
    }

    private fun mostrarMensajeExito(){
        Toast.makeText(this,"Itinerario editado con éxito.", Toast.LENGTH_SHORT).show()
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
    private fun showDatePickerDialogFin() {
        val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            calendarFin.set(Calendar.YEAR, year)
            calendarFin.set(Calendar.MONTH, month)
            calendarFin.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateFechaFinEditText()
        }, calendarFin.get(Calendar.YEAR), calendarFin.get(Calendar.MONTH), calendarFin.get(
            Calendar.DAY_OF_MONTH))
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

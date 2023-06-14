package com.dapm.gotour.itinerarios

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.RegistroDestino
import com.dapm.gotour.databinding.ActivityAgregarDestinoBinding
import com.dapm.gotour.databinding.ActivityEditarRegistroBinding
import java.text.SimpleDateFormat
import java.util.*

class EditarRegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarRegistroBinding

    private lateinit var dbHandler: DataBaseHandler

    private lateinit var etFecha: EditText

    private val calendarInicio: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_registro)
        binding = ActivityEditarRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHandler = DataBaseHandler(this)


        val nameDestino = findViewById<TextView>(R.id.destino_nombre)

        val idRegistro = intent.getIntExtra("idRegistro", -1)
        val namee = intent.getStringExtra("nombre_destinazo")

        nameDestino.text = namee
        val registro = dbHandler.buscarRegistroPorId(idRegistro)!!


        etFecha= findViewById(R.id.etFecha)

        val Fecha = intent.getStringExtra("fecha")


        etFecha.setText(Fecha)
        etFecha.setOnClickListener { showDatePickerDialogInicio() }

        binding.apply {

            aceptarEditar.setOnClickListener {

                val registroEditado = RegistroDestino(registro.id_registro, registro.id_destino, registro.id_itinerario, etFecha.text.toString())
                dbHandler.editarRegistro(registroEditado)

                val intent = Intent()
                intent.putExtra("idRegistro", registro.id_registro)
                intent.putExtra("idItinerario", registro.id_itinerario)
                intent.putExtra("fechaActualizada", etFecha.text.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

            cancelarEditar.setOnClickListener{
                finish()
            }

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
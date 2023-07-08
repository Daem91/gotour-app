package com.dapm.gotour.resenas

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.Resena
import com.dapm.gotour.database.model.Usuario
import com.dapm.gotour.databinding.ActivityEditarItinerarioBinding
import com.dapm.gotour.databinding.ActivityEditarResenaBinding

class EditarResenaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarResenaBinding
    private lateinit var dbHandler: DataBaseHandler
    private lateinit var etResena: EditText
    private lateinit var resenaAntigua: Resena

    companion object {
        const val REQUEST_CODE_EDITAR_RESENA = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarResenaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHandler = DataBaseHandler(this)
        etResena = findViewById(R.id.edit_text)
        val id = intent.getIntExtra("id_resena", -1)
        resenaAntigua = dbHandler.obtenerResenaPorIdResena(id)!!
        etResena.setText(resenaAntigua.descripcion)

        binding.apply {

            cancelarEditar.setOnClickListener { finish() }
            aceptarEditar.setOnClickListener {
                val contenido = etResena.text.toString()

                if (contenido.isNotEmpty()) {
                    val idResena = intent.getIntExtra("id_resena", -1)
                    val resenaAntigua = dbHandler.obtenerResenaPorIdResena(idResena)!!
                    val resenaNueva = Resena(id_resena =  idResena, descripcion = contenido,
                        id_destino = resenaAntigua.id_destino, username = Usuario.username, fecha = resenaAntigua.fecha)
                    dbHandler.modificarResena(resenaNueva)
                    val intent = Intent()
                    intent.putExtra("idResena", resenaNueva.id_resena)
                    intent.putExtra("descripcion", resenaNueva.descripcion)
                    intent.putExtra("idDestino", resenaNueva.id_destino)
                    intent.putExtra("username", resenaNueva.username)
                    intent.putExtra("fecha", resenaNueva.fecha)
                    setResult(Activity.RESULT_OK, intent)
                    Toast.makeText(root.context, "Reseña actualizada exitosamente.", Toast.LENGTH_SHORT).show()
                    finish()

                } else {
                    Toast.makeText(root.context, "Tienes que ingresar algo.", Toast.LENGTH_SHORT).show()
                }

            }

        }

    }
}
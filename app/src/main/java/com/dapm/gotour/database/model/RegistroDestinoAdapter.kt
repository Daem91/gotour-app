package com.dapm.gotour.database.model

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.itinerarios.EditarRegistroActivity
import com.dapm.gotour.itinerarios.VerItinerarioActivity

class RegistroDestinoAdapter(private var destinos: List<Triple<Int, String, String>>, val idItinerario: Int) : RecyclerView.Adapter<RegistroDestinoAdapter.RegistroDestinoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroDestinoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ver_registros_card_layout, parent, false)
        return RegistroDestinoViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: RegistroDestinoViewHolder, position: Int) {
        val destino = destinos[position]
        holder.bindView(destino)
    }

    override fun getItemCount(): Int {
        return destinos.size
    }

    fun registroEliminado(position: Int) {
        destinos.toMutableList().removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, destinos.size)

    }

    fun getId(): Int {
        return idItinerario
    }

    fun actualizarDatos(nuevaLista: List<Triple<Int, String, String>>) {
        destinos = nuevaLista
        notifyDataSetChanged()
    }

    class RegistroDestinoViewHolder(itemView: View, adapter: RegistroDestinoAdapter) : RecyclerView.ViewHolder(itemView) {
        private var adapter: RegistroDestinoAdapter
        private val nombreTextView: TextView = itemView.findViewById(R.id.txtRegistro)
        private val fechaTextView: TextView = itemView.findViewById(R.id.txtFecha)
        private val eliminarButton: View = itemView.findViewById(R.id.delete_button)
        private val editarButton: View = itemView.findViewById(R.id.edit_button)

        init {
            this.adapter = adapter
        }

        companion object {
            private const val REQUEST_CODE_EDITAR_REGISTRO = 1
        }

        fun bindView(destino: Triple<Int, String, String>) {
            nombreTextView.text = destino.second
            fechaTextView.text = destino.third

            editarButton.setOnClickListener {

                val intent = Intent(itemView.context, EditarRegistroActivity::class.java)
                intent.putExtra("idRegistro", destino.first)
                intent.putExtra("nombre_destinazo",destino.second)
                intent.putExtra("fecha",destino.third)
                (itemView.context as Activity).startActivityForResult(intent, REQUEST_CODE_EDITAR_REGISTRO)

            }

            eliminarButton.setOnClickListener {
                eliminarDestinoItinerario(destino.first, adapter)
            }
        }

        fun eliminarDestinoItinerario(idRegistro: Int, adapter: RegistroDestinoAdapter) {
            var itinerario: Int

            val dbHandler = DataBaseHandler(itemView.context)
            dbHandler.eliminarRegistro(idRegistro)
            Toast.makeText(itemView.context, "Se quitó del itinerario.", Toast.LENGTH_SHORT).show()
            (itemView.context as Activity).runOnUiThread {
                (adapter).registroEliminado(bindingAdapterPosition)
                itinerario = (adapter).getId()
                val listaza = dbHandler.obtenerDestinosRegistradosPorItinerario(itinerario)
                (adapter).actualizarDatos(listaza)
            }


        }

    }
}

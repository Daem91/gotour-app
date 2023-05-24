package com.dapm.gotour.database.model

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dapm.gotour.R
import com.dapm.gotour.itinerarios.VerItinerarioActivity

class RegistroDestinoAdapter(private val nombresDestino: List<String>) : RecyclerView.Adapter<RegistroDestinoAdapter.RegistroDestinoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroDestinoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ver_registros_card_layout, parent, false)
        return RegistroDestinoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegistroDestinoViewHolder, position: Int) {
        val nombreDestino = nombresDestino[position]
        holder.bindView(nombreDestino)
    }

    override fun getItemCount(): Int {
        return nombresDestino.size
    }

    class RegistroDestinoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.txtRegistro)

        fun bindView(nombreDestino: String) {
            nombreTextView.text = nombreDestino
        }
    }
}

package com.dapm.gotour.database.model


import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.itinerarios.EditarItinerarioActivity
import com.dapm.gotour.itinerarios.EditarRegistroActivity

import com.dapm.gotour.itinerarios.ItinerariosActivity
import com.dapm.gotour.itinerarios.VerItinerarioActivity

class ItinerarioAdapter:RecyclerView.Adapter<ItinerarioAdapter.ItinerarioViewHolder>() {

    private var itinerarioList:ArrayList<Itinerario> =ArrayList()

    fun addItems(items:ArrayList<Itinerario>){
        this.itinerarioList=items
        notifyDataSetChanged()
    }
    fun registroEliminado(position: Int) {
        itinerarioList.toMutableList().removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itinerarioList.size)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItinerarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itinerario_card_layout, parent, false)
        return ItinerarioViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: ItinerarioViewHolder, position: Int) {
        val iti = itinerarioList[position]
        holder.bindView(iti)
    }
    fun actualizarDatosItinerario(nuevaLista: ArrayList<Itinerario>) {
        itinerarioList = nuevaLista
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itinerarioList.size
    }

    class ItinerarioViewHolder(var view: View, adapter: ItinerarioAdapter):RecyclerView.ViewHolder(view){

        private var adapter:ItinerarioAdapter

        private var idItinerario=view.id
        private var nombre=view.findViewById<TextView>(R.id.tvName)
        private var fecha_incio=view.findViewById<TextView>(R.id.tvIncio)
        private var fecha_fin=view.findViewById<TextView>(R.id.tvFin)
        private var btnVer=view.findViewById<ImageButton>(R.id.btnVer)
        private var btnEdit=view.findViewById<ImageButton>(R.id.btnEdit)
        private var btnDelete=view.findViewById<ImageButton>(R.id.btnDelete)
        init {
            this.adapter=adapter
        }
        companion object {
             private const val REQUEST_CODE_EDITAR_ITINERARIO = 1
        }


        fun bindView(iti: Itinerario){

            nombre.text=iti.nombre
            fecha_incio.text=iti.fecha_inicio
            fecha_fin.text=iti.fecha_fin


            btnVer.setOnClickListener {
                val intent = Intent(view.context, VerItinerarioActivity::class.java)
                intent.putExtra("id_itinerario",iti.id_itinerario)
                intent.putExtra("name_itinerario",iti.nombre)
                intent.putExtra("fechaI",iti.fecha_inicio)
                intent.putExtra("fechaF",iti.fecha_fin)

                view.context.startActivity(intent)
            }
            btnEdit.setOnClickListener {
                val intent = Intent(view.context, EditarItinerarioActivity::class.java)
                intent.putExtra("id_itinerario", iti.id_itinerario)
                intent.putExtra("name_itinerario", iti.nombre)
                intent.putExtra("fechaI", iti.fecha_inicio)
                intent.putExtra("fechaF", iti.fecha_fin)
                (itemView.context as Activity).startActivityForResult(intent,
                    REQUEST_CODE_EDITAR_ITINERARIO)



            }
            btnDelete.setOnClickListener {
                val dbHandler = DataBaseHandler(itemView.context)
                val itinerarioId = iti.id_itinerario ?: -1 // Valor predeterminado en caso de que iti.id_itinerario sea nulo
                dbHandler.eliminarItinerarioYRegistrosPorId(itinerarioId)
                Toast.makeText(itemView.context, "Se eliminó el itinerario.", Toast.LENGTH_SHORT).show()
                (itemView.context as Activity).runOnUiThread {
                    adapter.registroEliminado(bindingAdapterPosition)
                }

                val listaza = dbHandler.obtenerItinerariosPorUsername(iti.username)
                (adapter).actualizarDatosItinerario(listaza)
            }


        }


    }




}
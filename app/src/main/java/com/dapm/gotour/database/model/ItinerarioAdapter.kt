package com.dapm.gotour.database.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dapm.gotour.R

class ItinerarioAdapter:RecyclerView.Adapter<ItinerarioAdapter.ItinerarioViewHolder>() {

    private var itinerarioList:ArrayList<Itinerario> =ArrayList()

    fun addItems(items:ArrayList<Itinerario>){
        this.itinerarioList=items
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ItinerarioViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.itinerario_card_layout, parent, false)
    )

    override fun onBindViewHolder(holder: ItinerarioViewHolder, position: Int) {
        val iti=itinerarioList[position]
        holder.bindView(iti)
    }

    override fun getItemCount(): Int {
        return itinerarioList.size
    }

    class ItinerarioViewHolder(var view: View):RecyclerView.ViewHolder(view){


        private var nombre=view.findViewById<TextView>(R.id.tvName)
        private var fecha_incio=view.findViewById<TextView>(R.id.tvIncio)
        private var fecha_fin=view.findViewById<TextView>(R.id.tvFin)

        fun bindView(iti: Itinerario){

            nombre.text=iti.nombre
            fecha_incio.text=iti.fecha_inicio
            fecha_fin.text=iti.fecha_fin

        }
    }

}
package com.dapm.gotour.database.model

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.dapm.gotour.R
import com.dapm.gotour.itinerarios.ItinerariosActivity
import com.dapm.gotour.itinerarios.VerItinerarioActivity

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
        private var btnVer=view.findViewById<Button>(R.id.btnVer)

        fun bindView(iti: Itinerario){

            nombre.text=iti.nombre
            fecha_incio.text=iti.fecha_inicio
            fecha_fin.text=iti.fecha_fin

            btnVer.setOnClickListener {
                val intent = Intent(view.context, VerItinerarioActivity::class.java)
                intent.putExtra("id_itinerario",iti.id_itinerario)
                intent.putExtra("name_itinerario",iti.nombre)
                view.context.startActivity(intent)
            }

        }
    }

}
package com.dapm.gotour.database.model

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.databinding.ResenaCardLayoutBinding
import com.dapm.gotour.resenas.EditarResenaActivity
import java.text.SimpleDateFormat
import java.util.*

class ResenaAdapter(private var resenas: List<Resena>):RecyclerView.Adapter<ResenaAdapter.ResenaHolder>() {

    private var resenasLista: MutableList<Resena> = mutableListOf()

    fun addItems(items:ArrayList<Resena>){
        this.resenasLista=items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResenaAdapter.ResenaHolder {
        val binding = ResenaCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResenaHolder(binding, this)
    }

    override fun onBindViewHolder(holder: ResenaAdapter.ResenaHolder, position: Int) {
        holder.enlazar(resenas[position])
    }

    override fun getItemCount(): Int {
        return resenas.size
    }

    fun resenaEliminada(position: Int) {
        resenasLista.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, resenas.size)

    }

    fun actualizarDatos(nuevaLista: List<Resena>) {
        resenasLista.clear()
        resenasLista.addAll(nuevaLista)
        notifyDataSetChanged()
    }

    class ResenaHolder ( val binding: ResenaCardLayoutBinding, adapter: ResenaAdapter) :
        RecyclerView.ViewHolder(binding.root) {

        private var adapter: ResenaAdapter

        init {
            this.adapter = adapter
        }

        companion object {
            private const val REQUEST_CODE_EDITAR_RESENA = 1
        }

        fun enlazar(resena: Resena) {

            binding.apply {
                username.text = resena.username
                resenaText.text = resena.descripcion
                println(resena.fecha)
                fechaResena.text = convertirFecha(resena.fecha)

                if (resena.username == Usuario.username) {
                    editButton.visibility = View.VISIBLE
                    deleteButton.visibility = View.VISIBLE
                } else {
                    editButton.visibility = View.GONE
                    deleteButton.visibility = View.GONE
                }

                editButton.setOnClickListener {
                    val intent = Intent(root.context, EditarResenaActivity::class.java)
                    intent.putExtra("id_resena", resena.id_resena)
                    (root.context as Activity).startActivityForResult(intent,
                        REQUEST_CODE_EDITAR_RESENA
                    )
                }

                deleteButton.setOnClickListener {
                    val id = resena.id_resena!!
                    eliminarResena(id, adapter)
                }

            }

        }

        fun convertirFecha(fechaString: String): String {
            val formatoActual = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
            val formatoDeseado = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            val fecha = formatoActual.parse(fechaString)
            val fechaFormateada = formatoDeseado.format(fecha!!)

            return fechaFormateada
        }


        fun eliminarResena(idResena: Int, adapter: ResenaAdapter) {

            val dbHandler = DataBaseHandler(itemView.context)
            val resena = dbHandler.obtenerResenaPorIdResena(idResena)
            dbHandler.eliminarResena(idResena)
            Toast.makeText(itemView.context, "Reseña eliminada exitosamente.", Toast.LENGTH_SHORT).show()
            (itemView.context as Activity).runOnUiThread {
                (adapter).resenaEliminada(bindingAdapterPosition)
                val listaza = dbHandler.obtenerResenasPorIdDestino(resena?.id_destino!!)
                (adapter).actualizarDatos(listaza)
            }


        }


    }



}
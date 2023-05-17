package com.dapm.gotour.database.model

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dapm.gotour.database.config.DataBaseHandler

import com.dapm.gotour.databinding.DestinoCardLayoutBinding
import com.dapm.gotour.home.DestinoActivity
import com.dapm.gotour.home.DetalleActivity
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class DestinoAdapter(private var destinos: List<Destino>) : RecyclerView.Adapter<DestinoAdapter.DestinoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinoHolder {
        val binding = DestinoCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DestinoHolder(binding)
    }

    fun setListaFiltrada(destinos: List<Destino>) {
        this.destinos = destinos
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DestinoHolder, position: Int) {
        holder.enlazar(destinos[position])
    }

    override fun getItemCount() = destinos.size

    inner class DestinoHolder ( val binding: DestinoCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun enlazar(destino: Destino) {

            binding.apply {
                destinoNombre.text = destino.nombre
                ubicacion.text = destino.ubicacion

                agregarImagen(imagenDestino, destino.imagen)


                root.setOnClickListener {

                    val intent = Intent(root.context, DetalleActivity::class.java)
                    intent.putExtra("id_destino", destino.id_destino)
                    root.context.startActivity(intent)
                }

            }


        }
    }

    fun agregarImagen(imageView: ImageView, imagen: String) {

        Glide.with(imageView).load(imagen)
            .transform(RoundedCornersTransformation(30, 0))
            .into(imageView)
    }

}




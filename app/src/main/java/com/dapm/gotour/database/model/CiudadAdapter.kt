package com.dapm.gotour.database.model

import android.content.Intent
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.config.DataBaseInitializer
import com.dapm.gotour.databinding.CiudadCardLayoutBinding
import com.dapm.gotour.home.DestinoActivity
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


class CiudadAdapter (private var ciudades: List<Ciudad>) : RecyclerView.Adapter<CiudadAdapter.CiudadHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CiudadHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CiudadCardLayoutBinding.inflate(inflater, parent, false)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ciudad_card_layout, parent, false)
        return CiudadHolder(binding)
    }

    fun setListaFiltrada(ciudades: List<Ciudad>) {
        this.ciudades = ciudades
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CiudadHolder, position: Int) {
        val ciudad = ciudades[position]
        holder.enlazar(ciudad)
    }

    override fun getItemCount(): Int {
        return ciudades.size
    }

    inner class CiudadHolder (
        val binding: CiudadCardLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun enlazar(ciudad: Ciudad) {
            binding.apply {
                ciudadNombre.text = ciudad.nombre + " " + ciudad.id_ciudad
                departamentoNombre.text = ciudad.departamento

                agregarImagen(imagenCiudad, ciudad.imagen)

                root.setOnClickListener {

                    val intent = Intent(root.context, DestinoActivity::class.java)
                    intent.putExtra("id_ciudad", ciudad.id_ciudad)
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
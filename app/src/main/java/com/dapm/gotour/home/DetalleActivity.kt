package com.dapm.gotour.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dapm.gotour.R
import com.dapm.gotour.database.config.DataBaseHandler
import com.dapm.gotour.database.model.Destino
import com.dapm.gotour.database.model.Favorito
import com.dapm.gotour.database.model.Usuario

import com.dapm.gotour.databinding.ActivityDetalleBinding
import com.dapm.gotour.itinerarios.AgregarDestinoActivity
import com.dapm.gotour.itinerarios.ItinerariosActivity
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

private val PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1

class DetalleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleBinding
    private lateinit var destino: Destino
    private lateinit var dataBaseHandler: DataBaseHandler
    private lateinit var favoritosUsuario: ArrayList<Destino>
    private var destinoEstaEnFavoritos: Boolean = false

    private val username = Usuario.username


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->

            menuItem.isChecked = true

            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.itinerarios -> {
                    val intent = Intent(this, ItinerariosActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.favoritos -> {
                    val intent = Intent(this, FavoritosActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                else -> false
            }
        }



        dataBaseHandler = DataBaseHandler(this)

        val id_destino = intent.getIntExtra("id_destino", -1)
        destino = dataBaseHandler.obtenerDestinoPorId(id_destino)!!

        val imageView = findViewById<ImageView>(R.id.imagen_destino)
        Glide.with(this).load(destino.imagen)
            .transform(RoundedCornersTransformation(30, 0))
            .into(imageView)

        val nombreDestino = findViewById<TextView>(R.id.destino_nombre)
        nombreDestino.text = destino.nombre

        val ubicacion = findViewById<TextView>(R.id.ubicacion)
        ubicacion.text = destino.ubicacion

        val descripcion = findViewById<TextView>(R.id.descripcion)
        descripcion.text = destino.descripcion
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            descripcion.justificationMode  = JUSTIFICATION_MODE_INTER_WORD
        }

        val tagsContainer = findViewById<FlexboxLayout>(R.id.tags_container)
        val tags = destino.tags

        for (tag in tags){
            val tagTextView = TextView(this)
            tagTextView.text = tag
            tagTextView.setPadding(16,8,16,8)
            tagTextView.setBackgroundResource(R.drawable.tag_background)
            val color = ContextCompat.getColor(this, R.color.orange)
            tagTextView.setTextColor(color)

            val layoutParams = FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(8, 8, 8, 8)
            tagTextView.layoutParams = layoutParams

            tagsContainer.addView(tagTextView)
        }


        val btnVerDestinoMapa: LinearLayout = binding.verMapaContainer

        btnVerDestinoMapa.setOnClickListener {
            val intent = Intent(this, VerDestinoMapa::class.java)
            intent.putExtra("latitud", destino.latitud)
            intent.putExtra("longitud", destino.longitud)
            intent.putExtra("nombre", destino.nombre)
            startActivity(intent)
        }


        //Favs de Usuario
        favoritosUsuario = dataBaseHandler.obtenerFavoritosPorUsername(username)


        binding.apply {
            destinoEstaEnFavoritos = favoritosUsuario.any { it.id_destino == id_destino }
            println(destinoEstaEnFavoritos)
            if (destinoEstaEnFavoritos){
                addFavButton.setBackgroundResource(R.drawable.favorite_filled)
            } else {
                addFavButton.setBackgroundResource(R.drawable.favorite)
            }
            addFavButton.setOnClickListener{
                if (destinoEstaEnFavoritos){
                    eliminarFavorito()
                } else {
                    agregarFavorito()
                }
            }

            favButton.setOnClickListener{
                val intent = Intent(this.root.context, AgregarDestinoActivity::class.java)
                intent.putExtra("id_destino",destino.id_destino)
                intent.putExtra("name_destino",destino.nombre)
                startActivity(intent)
            }

        }

    }

    private fun agregarFavorito(){
        val id_destino = intent.getIntExtra("id_destino", -1)
        val favorito = Favorito(null, username, id_destino)
        dataBaseHandler.añadirFavorito(favorito)

        binding.addFavButton.setBackgroundResource(R.drawable.favorite_filled)
        Log.e("Favorito", "ID Favorito: ${favorito.id_favorito}, Usuario: ${favorito.username}, ID Destino: ${favorito.id_destino}")
        mostrarMensajeAgregado()
        destinoEstaEnFavoritos = true
        favoritosUsuario = dataBaseHandler.obtenerFavoritosPorUsername(username)
    }

    private fun eliminarFavorito(){
        val id_destino = intent.getIntExtra("id_destino", -1)
        val favorito = Favorito(null, username, id_destino)
        dataBaseHandler.eliminarFavorito(favorito)

        binding.addFavButton.setBackgroundResource(R.drawable.favorite)
        mostrarMensajeEliminado()
        destinoEstaEnFavoritos = false
        favoritosUsuario = dataBaseHandler.obtenerFavoritosPorUsername(username)
    }
    private fun mostrarMensajeAgregado(){
        Toast.makeText(this,"Destino añadido a favoritos.", Toast.LENGTH_SHORT).show()
    }
    private fun mostrarMensajeEliminado(){
        Toast.makeText(this,"Este destino se eliminó de favoritos.", Toast.LENGTH_SHORT).show()
    }
    private fun mostrarMensajeError(){
        Toast.makeText(this,"ERROR: No se ha podido añadir el destino a favoritos.", Toast.LENGTH_SHORT).show()
    }

}
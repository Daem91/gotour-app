package com.dapm.gotour.database.config

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.dapm.gotour.R
import com.dapm.gotour.database.model.Ciudad
import com.dapm.gotour.database.model.Destino
import java.io.File
import java.io.FileOutputStream

class DataBaseInitializer {

    //private val PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 1

    fun inicializarCiudades(dataBaseHandler: DataBaseHandler) {

        if (dataBaseHandler.listarCiudades().isEmpty()) {
            val ciudades = listOf(
                Ciudad(null, "Trujillo", "La Libertad", "https://www.guiarepsol.com/content/dam/repsol-guia/contenidos-imagenes/viajar/vamos-de-excursion/10-claves-para-visitar-trujillo-que-visitar-donde-comer/gr-cms-media-featured_images-none-257a11c4-b69e-40c4-b177-c812d821459a-vistaapertura1.jpg"),
                Ciudad(null, "Arequipa", "Arequipa", "https://travel1tours.com/wp-content/uploads/2019/10/Arequipa.jpg"),
                Ciudad(null, "Iquitos", "Loreto", "https://res.cloudinary.com/rainforest-cruises/images/c_fill,g_auto/f_auto,q_auto/w_1120,h_732,c_fill,g_auto/v1622583203/peru-blogs-afaf/peru-blogs-afaf-1120x732.jpg"),
                Ciudad(null, "Lima", "Lima", "https://www.esan.edu.pe/images/blog/2018/10/15/x1500x844-lima-2035-2050.jpg.pagespeed.ic.VZFjFf5c4p.jpg")
            )

            for (ciudad in ciudades) {
                dataBaseHandler.crearCiudad(ciudad)
            }
        }

    }

    fun inicializarDestinos(context: Context, dataBaseHandler: DataBaseHandler, idCiudad: Int) {
        if (dataBaseHandler.obtenerDestinos(idCiudad).isEmpty()) {
            val destinos = mutableListOf<Destino>()
            if (idCiudad == 1) {

                destinos.add(Destino(null, "Ciudadela de Chan Chan", "Chan Chan es una ciudad precolombina de adobe, construida en la costa norte del Perú por los chimúes. Es la ciudad construida en adobe más grande de América y del mundo." , "VWVG+Q5C, Huanchaco", "https://www.wamanadventures.com/blog/wp-content/uploads/2019/01/Chan-Chan-Waman-Adventures-3.jpg",1))
                destinos.add(Destino(null, "Plaza de Armas de Trujillo", "Plaza ajardinada animada con una escultura central en honor a la independencia de Trujillo en 1820." , "Plaza Mayor de Trujillo, Trujillo 13001", "https://a.travel-assets.com/findyours-php/viewfinder/images/res70/66000/66754-Trujillo-Plaza-De-Armas.jpg",1))
                destinos.add(Destino(null, "Casa de la Emancipación", "La Casa de la Emancipación, ubicada a una cuadra de la Plaza de Armas de Trujillo, recibe su nombre porque en ella se proclamó la Independencia del Perú el 29 de diciembre de 1820." , "Jr. Gamarra, Trujillo 13001", "https://fundacionbbva.pe/wp-content/uploads/2018/06/Casa-emancipacion-trujillo01-1600x600.jpg",1))

            }

            for (destino in destinos) {
                dataBaseHandler.crearDestino(destino)
            }
        }
    }

    /*
    private fun guardarImagenEnArchivo(context: Context, bitmap: Bitmap, nombreArchivo: String): String {
        val permiso = ContextCompat.checkSelfPermission(context, Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        if (permiso == PackageManager.PERMISSION_GRANTED) {
            val directorioImagenes = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "gotour")
            if (!directorioImagenes.exists()) {
                directorioImagenes.mkdirs()
            }
            val archivoImagen = File(directorioImagenes, nombreArchivo)
            val fileOutputStream = FileOutputStream(archivoImagen)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
            return archivoImagen.absolutePath
        } else {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE)
            return ""
        }
    }
    */


}


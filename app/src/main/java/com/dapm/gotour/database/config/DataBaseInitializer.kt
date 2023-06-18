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

                destinos.add(Destino(null, "Ciudadela de Chan Chan", "Chan Chan es una ciudad precolombina de adobe, construida en la costa norte del Perú por los chimúes. Es la ciudad construida en adobe más grande de América y del mundo." , "VWVG+Q5C, Huanchaco", "https://www.wamanadventures.com/blog/wp-content/uploads/2019/01/Chan-Chan-Waman-Adventures-3.jpg",1,"-8.0974264", "-79.0694712"))
                destinos.add(Destino(null, "Plaza de Armas de Trujillo", "Plaza ajardinada animada con una escultura central en honor a la independencia de Trujillo en 1820." , "Plaza Mayor de Trujillo, Trujillo 13001", "https://a.travel-assets.com/findyours-php/viewfinder/images/res70/66000/66754-Trujillo-Plaza-De-Armas.jpg",1,"-8.1117713", "-79.0312746"))
                destinos.add(Destino(null, "Casa de la Emancipación", "La Casa de la Emancipación, ubicada a una cuadra de la Plaza de Armas de Trujillo, recibe su nombre porque en ella se proclamó la Independencia del Perú el 29 de diciembre de 1820." , "Jr. Gamarra, Trujillo 13001", "https://fundacionbbva.pe/wp-content/uploads/2018/06/Casa-emancipacion-trujillo01-1600x600.jpg",1,"-8.1105594", "-79.0291891"))

            }
            if (idCiudad == 2) {

                destinos.add(Destino(null, "Catedral de Arequipa", "Recorre más de 400 años de historia descubriendo el interior de la Catedral de Arequipa, así como piezas de orfebrería, ornamentos, pinturas y una visita al campanario de la Catedral para ver la ciudad y los volcanes desde esta altura" , "Plaza de Armas, Arequipa 04001", "https://upload.wikimedia.org/wikipedia/commons/5/5f/Catedral_Arequipa%2C_Peru.jpg",2,"-16.398677", "-71.536461"))
                destinos.add(Destino(null, "El valle y el cañón del Colca", "El Cañón del Colca es el mejor lugar de Sudamérica para ver al majestuoso Cóndor de los Andes. También es uno de los destinos más escogidos para hacer trekking en Perú.", "Chivay, Arequipa", "https://upload.wikimedia.org/wikipedia/commons/d/da/Valley_of_Colca_River%2C_Peru.jpg",2,"-15.641725", "-71.887133"))
                destinos.add(Destino(null, "Sillar de Arequipa", "Los impresionantes muros formados con material volcánico son uno de los lugares por visitar en Arequipa menos concurridos, pero más impresionantes. En este paseo podrás ver las canteras activas de Añashuayco y aprender cómo se construyó toda una ciudad con este material. Por eso se conoce a Arequipa como “La Ciudad Blanca”." , "Canteras Añashuayco, Cerro Colorado 04017", "https://miviajeporperu.com/wp-content/uploads/2021/08/ruta-del-sillar-arequipa.jpg",2,"-16.423650", "-71.489575"))

            }
            if (idCiudad == 3) {

                destinos.add(Destino(null, "Reserva Nacional Pacaya Samiria", "Reserva protegida del Perú, y una de las zonas turísticas por excelencia en América del Sur, esta porción de tierra con increíbles paisajes turísticos vive sus días como el bosque húmedo tropical que cualquier amante de la naturaleza puede anhelar." , "Calle PV. # 12 Tibilo Villa, Lagunas 16551", "https://upload.wikimedia.org/wikipedia/commons/4/49/Aldea_del_Mara%C3%B1on_-_panoramio.jpg",3,"-5.678089", "-74.113127"))
                destinos.add(Destino(null, "Complejo Turístico Quistococha", "El Complejo Quistococha es una de las reservas naturales más concurridas cada fin de semana por aquellos que gozan de organizar un picnic, dar paseos en bote a remo o simplemente disfrutar del turismo en Iquitos. Ubicado cerca de la laguna Quistococha, el sitio también ha sido escenario para complejos trabajos de investigación, reforestación y conservación." , "Quistococha, 16000", "https://upload.wikimedia.org/wikipedia/commons/0/02/Quistococha2.jpg",3,"-3.767436", "-73.250034"))
                destinos.add(Destino(null, "La isla de los monos", "Este increíble sitio para conocer está compuesto por 450 hectáreas repletas de vegetación silvestre. Según cuenta la historia, los primeros diez años del proyecto fueron utilizados solamente para forestar la isla. En medio de este paisaje turístico es posible disfrutar la presencia del Mono Tocón, el Aullador, el Araña, el Leoncito y el infaltable mono Choro, una de las especies más simpáticas. " , "Loreto 380, Iquitos 16002", "https://portal.andina.pe/EDPfotografia3/Thumbnail/2017/09/27/000452785W.jpg",3,"-3.757220", "-73.259631"))

            }
            if (idCiudad == 4) {

                destinos.add(Destino(null, "Museo Larco", "Uno de los mejores lugares para absorber cultura peruana es visitando el Museo Larco. Rodeado de hermosos jardines, esta mansión virreinal posee más de 40 mil piezas de arte que datan de hasta 5 mil años de antigüedad." , "Av. Simón Bolívar 1515, Pueblo Libre 15084", "https://denomades.s3.us-west-2.amazonaws.com/blog/wp-content/uploads/2020/08/30150402/museo-larco-garden-1024x629.jpg",4,"-12.075727", "-77.064574"))
                destinos.add(Destino(null, "Malecón de Miraflores", "Personas caminando por Malecón de MirafloresUn listado de los mejores lugares turísticos de Lima no estaría completo si no incorporáramos al Malecón de Miraflores. Aquí la ciudad se encuentra con el mar, y donde puedes caminar por el lado de gigantescos acantilados que miran hacia el Pacífico. No olvides visitar Barranco, ubicado más al sur, pues es uno de los barrios más pintorescos e interesantes de Lima." , "Mal. de la Reserva 275, Miraflores 15074", "https://denomades.s3.us-west-2.amazonaws.com/blog/wp-content/uploads/2020/08/30144634/Maleco%CC%81n-1024x588.jpeg",4,"-12.146892", "-77.024491"))
                destinos.add(Destino(null, "Punta Negra", "Ubicada al sur de Lima, Punta Negra es un balneario que cuenta con una serie de playas hermosas. Un destino espectacular si te interesa el surf, ya que sus olas son de las mejores del distrito. Si estás buscando un retiro en la naturaleza, la playa Punta Negra posee rocas inmensas y oscuras que ofrecen un paisaje particularmente hermoso al atardecer." , "Punta Negra, Lima", "https://denomades.s3.us-west-2.amazonaws.com/blog/wp-content/uploads/2020/08/30142620/8465206423_df0e695ed2_b.jpg",4, "-12.354487", "-76.778952"))

            }
            if (idCiudad == 5) {
                destinos.add(Destino(null, "Ciudadela de Chan Chan", "Chan Chan es una ciudad precolombina de adobe, construida en la costa norte del Perú por los chimúes. Es la ciudad construida en adobe más grande de América y del mundo." , "VWVG+Q5C, Huanchaco", "https://www.wamanadventures.com/blog/wp-content/uploads/2019/01/Chan-Chan-Waman-Adventures-3.jpg",1,"-8.0974264", "-79.0694712"))
                destinos.add(Destino(null, "Plaza de Armas de Trujillo", "Plaza ajardinada animada con una escultura central en honor a la independencia de Trujillo en 1820." , "Plaza Mayor de Trujillo, Trujillo 13001", "https://a.travel-assets.com/findyours-php/viewfinder/images/res70/66000/66754-Trujillo-Plaza-De-Armas.jpg",1,"-8.1117713", "-79.0312746"))
                destinos.add(Destino(null, "Casa de la Emancipación", "La Casa de la Emancipación, ubicada a una cuadra de la Plaza de Armas de Trujillo, recibe su nombre porque en ella se proclamó la Independencia del Perú el 29 de diciembre de 1820." , "Jr. Gamarra, Trujillo 13001", "https://fundacionbbva.pe/wp-content/uploads/2018/06/Casa-emancipacion-trujillo01-1600x600.jpg",1,"-8.1105594", "-79.0291891"))

                destinos.add(Destino(null, "Catedral de Arequipa", "Recorre más de 400 años de historia descubriendo el interior de la Catedral de Arequipa, así como piezas de orfebrería, ornamentos, pinturas y una visita al campanario de la Catedral para ver la ciudad y los volcanes desde esta altura" , "Plaza de Armas, Arequipa 04001", "https://upload.wikimedia.org/wikipedia/commons/5/5f/Catedral_Arequipa%2C_Peru.jpg",2,"-16.398677", "-71.536461"))
                destinos.add(Destino(null, "El valle y el cañón del Colca", "El Cañón del Colca es el mejor lugar de Sudamérica para ver al majestuoso Cóndor de los Andes. También es uno de los destinos más escogidos para hacer trekking en Perú.", "Chivay, Arequipa", "https://upload.wikimedia.org/wikipedia/commons/d/da/Valley_of_Colca_River%2C_Peru.jpg",2,"-15.641725", "-71.887133"))
                destinos.add(Destino(null, "Sillar de Arequipa", "Los impresionantes muros formados con material volcánico son uno de los lugares por visitar en Arequipa menos concurridos, pero más impresionantes. En este paseo podrás ver las canteras activas de Añashuayco y aprender cómo se construyó toda una ciudad con este material. Por eso se conoce a Arequipa como “La Ciudad Blanca”." , "Canteras Añashuayco, Cerro Colorado 04017", "https://miviajeporperu.com/wp-content/uploads/2021/08/ruta-del-sillar-arequipa.jpg",2,"-16.423650", "-71.489575"))

                destinos.add(Destino(null, "Reserva Nacional Pacaya Samiria", "Reserva protegida del Perú, y una de las zonas turísticas por excelencia en América del Sur, esta porción de tierra con increíbles paisajes turísticos vive sus días como el bosque húmedo tropical que cualquier amante de la naturaleza puede anhelar." , "Calle PV. # 12 Tibilo Villa, Lagunas 16551", "https://upload.wikimedia.org/wikipedia/commons/4/49/Aldea_del_Mara%C3%B1on_-_panoramio.jpg",3,"-5.678089", "-74.113127"))
                destinos.add(Destino(null, "Complejo Turístico Quistococha", "El Complejo Quistococha es una de las reservas naturales más concurridas cada fin de semana por aquellos que gozan de organizar un picnic, dar paseos en bote a remo o simplemente disfrutar del turismo en Iquitos. Ubicado cerca de la laguna Quistococha, el sitio también ha sido escenario para complejos trabajos de investigación, reforestación y conservación." , "Quistococha, 16000", "https://upload.wikimedia.org/wikipedia/commons/0/02/Quistococha2.jpg",3,"-3.767436", "-73.250034"))
                destinos.add(Destino(null, "La isla de los monos", "Este increíble sitio para conocer está compuesto por 450 hectáreas repletas de vegetación silvestre. Según cuenta la historia, los primeros diez años del proyecto fueron utilizados solamente para forestar la isla. En medio de este paisaje turístico es posible disfrutar la presencia del Mono Tocón, el Aullador, el Araña, el Leoncito y el infaltable mono Choro, una de las especies más simpáticas. " , "Loreto 380, Iquitos 16002", "https://portal.andina.pe/EDPfotografia3/Thumbnail/2017/09/27/000452785W.jpg",3,"-3.757220", "-73.259631"))

                destinos.add(Destino(null, "Museo Larco", "Uno de los mejores lugares para absorber cultura peruana es visitando el Museo Larco. Rodeado de hermosos jardines, esta mansión virreinal posee más de 40 mil piezas de arte que datan de hasta 5 mil años de antigüedad." , "Av. Simón Bolívar 1515, Pueblo Libre 15084", "https://denomades.s3.us-west-2.amazonaws.com/blog/wp-content/uploads/2020/08/30150402/museo-larco-garden-1024x629.jpg",4,"-12.075727", "-77.064574"))
                destinos.add(Destino(null, "Malecón de Miraflores", "Personas caminando por Malecón de MirafloresUn listado de los mejores lugares turísticos de Lima no estaría completo si no incorporáramos al Malecón de Miraflores. Aquí la ciudad se encuentra con el mar, y donde puedes caminar por el lado de gigantescos acantilados que miran hacia el Pacífico. No olvides visitar Barranco, ubicado más al sur, pues es uno de los barrios más pintorescos e interesantes de Lima." , "Mal. de la Reserva 275, Miraflores 15074", "https://denomades.s3.us-west-2.amazonaws.com/blog/wp-content/uploads/2020/08/30144634/Maleco%CC%81n-1024x588.jpeg",4,"-12.146892", "-77.024491"))
                destinos.add(Destino(null, "Punta Negra", "Ubicada al sur de Lima, Punta Negra es un balneario que cuenta con una serie de playas hermosas. Un destino espectacular si te interesa el surf, ya que sus olas son de las mejores del distrito. Si estás buscando un retiro en la naturaleza, la playa Punta Negra posee rocas inmensas y oscuras que ofrecen un paisaje particularmente hermoso al atardecer." , "Punta Negra, Lima", "https://denomades.s3.us-west-2.amazonaws.com/blog/wp-content/uploads/2020/08/30142620/8465206423_df0e695ed2_b.jpg",4,"-12.354487", "-76.778952"))

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


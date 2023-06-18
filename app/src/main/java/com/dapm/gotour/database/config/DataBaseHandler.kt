package com.dapm.gotour.database.config

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.preference.PreferenceManager
import android.widget.Toast
import com.dapm.gotour.database.model.*
import java.lang.Exception

val DATABASE_NAME = "GoTout_DB"

val COL_USERNAME = "username"
val COL_PASS = "password"
val TABLE_USUARIO = "Usuario"
val TABLE_FAVORITOS = "Favoritos"
val TABLE_CIUDAD = "Ciudad"
val TABLE_DESTINO = "Destino"
val TABLE_ITINERARIO = "Itinerario"
val TABLE_REGISTRO="RegistroDestino"
val TABLE_RESENA = "Resena"


val KEY_CIUDAD = "id_ciudad"
val NAME_CIUDAD = "nombre"
val DEPARTAMENTO_CIUDAD = "departamento"

class DataBaseHandler(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createUser = "CREATE TABLE Usuario(username TEXT PRIMARY KEY, password TEXT)";
        val createFavoritos = "CREATE TABLE Favoritos(id_favorito INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, id_destino INTEGER, FOREIGN KEY(username) REFERENCES Usuario(username),FOREIGN KEY(id_destino) REFERENCES Destino(id_destino))";
        val createCity = "CREATE TABLE Ciudad(id_ciudad INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, departamento TEXT, imagen TEXT)";
        val createDestination = "CREATE TABLE Destino(id_destino INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, ubicacion TEXT, imagen TEXT, id_ciudad INTEGER, latitud TEXT, longitud TEXT, FOREIGN KEY(id_ciudad) REFERENCES Ciudad(id_ciudad))"
        val createItinerario = "CREATE TABLE Itinerario(id_itinerario INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, fecha_inicio TEXT, fecha_fin TEXT,  username TEXT, FOREIGN KEY(username) REFERENCES Usuario(username))"
        val createRegistro = "CREATE TABLE RegistroDestino(id_registro INTEGER PRIMARY KEY AUTOINCREMENT, id_destino INTEGER, id_itinerario INTEGER, fecha TEXT, FOREIGN KEY(id_destino) REFERENCES Destino(id_destino), FOREIGN KEY(id_itinerario) REFERENCES Itinerario(id_itinerario))"
        val createResena = "CREATE TABLE Resena(id_resena INTEGER PRIMARY KEY AUTOINCREMENT, id_destino INTEGER, username TEXT, descripcion TEXT, FOREIGN KEY(id_destino) REFERENCES Destino(id_destino), FOREIGN KEY(username) REFERENCES Usuario(username))"
        db?.execSQL(createUser)
        db?.execSQL(createFavoritos)
        db?.execSQL(createCity)
        db?.execSQL(createDestination)
        db?.execSQL(createItinerario)
        db?.execSQL(createRegistro)
        db?.execSQL(createResena)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITOS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CIUDAD")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_DESTINO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_DESTINO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ITINERARIO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_REGISTRO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_RESENA")

    }


    // ----------------------
    // | METODOS DE USUARIO |
    // ----------------------

    fun createUsuario(usuario: Usuario): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_USERNAME, Usuario.username)
        cv.put(COL_PASS, usuario.contrasena)
        val resultado = db.insert(TABLE_USUARIO, null, cv)

        if (resultado == (-1).toLong()){
            return false
        } else {
            return true
        }
    }

    fun comprobarUsuario(usuario: Usuario): Boolean {
        val username = Usuario.username
        val contrasena = usuario.contrasena
        val db = this.writableDatabase
        val query = "SELECT * FROM Usuario WHERE username = '$username' " +
                "AND PASSWORD = '$contrasena'"
        val cursor = db.rawQuery(query, null)

        if (cursor.count <= 0){
            cursor.close()
            return false
        }

        cursor.close()
        return true

    }

    // ----------------------
    // | METODOS DE CIUDAD  |
    // ----------------------

    fun crearCiudad(ciudad: Ciudad) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("nombre", ciudad.nombre)
        cv.put("departamento", ciudad.departamento)
        cv.put("imagen", ciudad.imagen)
        db.insert(TABLE_CIUDAD, null, cv)
        db.close()

    }

    fun obtenerCiudadPorId(idCiudad: Int): Ciudad? {
        val db = this.writableDatabase
        val query = "SELECT * FROM Ciudad WHERE id_ciudad = ?"
        val cursor = db.rawQuery(query, arrayOf(idCiudad.toString()))

        var ciudad: Ciudad? = null
        if (cursor.moveToFirst()) {
            val id_ciudad = cursor.getInt(cursor.getColumnIndexOrThrow("id_ciudad"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val departamento = cursor.getString(cursor.getColumnIndexOrThrow("departamento"))
            val imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
            ciudad = Ciudad(id_ciudad, nombre, departamento, imagen)
        }
        cursor.close()

        return ciudad
    }

    @SuppressLint("Range")
    fun listarCiudades(): List<Ciudad> {
        val ciudades = mutableListOf<Ciudad>()
        val query = "SELECT * FROM $TABLE_CIUDAD"
        val db = this.readableDatabase

        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_CIUDAD))
                val nombre = cursor.getString(cursor.getColumnIndex(NAME_CIUDAD))
                val departamento = cursor.getString(cursor.getColumnIndex(DEPARTAMENTO_CIUDAD))
                val imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
                ciudades.add(Ciudad(id, nombre, departamento, imagen))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return ciudades
    }

    // ----------------------
    // | METODOS DE DESTINO |
    // ----------------------

    fun crearDestino(destino: Destino) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("nombre", destino.nombre)
        cv.put("descripcion", destino.descripcion)
        cv.put("ubicacion", destino.ubicacion)
        cv.put("imagen", destino.imagen)
        cv.put("id_ciudad", destino.id_ciudad)
        cv.put("latitud", destino.latitud)
        cv.put("longitud", destino.longitud)
        db.insert(TABLE_DESTINO, null, cv)
        db.close()
    }

    fun obtenerDestinos(idCiudad: Int): List<Destino> {
        val destinos = mutableListOf<Destino>()
        val db = this.readableDatabase
        val query = "SELECT * FROM Destino WHERE id_ciudad = ?"
        val selectionArgs = arrayOf(idCiudad.toString())
        val cursor = db.rawQuery(query, selectionArgs)
        while (cursor.moveToNext()) {
            val id_destino = cursor.getInt(cursor.getColumnIndexOrThrow("id_destino"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
            val ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion"))
            val imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
            val latitud = cursor.getString(cursor.getColumnIndexOrThrow("latitud"))
            val longitud = cursor.getString(cursor.getColumnIndexOrThrow("longitud"))
            destinos.add(Destino(id_destino, nombre, descripcion, ubicacion, imagen, idCiudad,latitud,longitud))
        }
        cursor.close()
        db.close()
        return destinos
    }

    fun obtenerDestinoPorId(idDestino: Int): Destino? {
        val db = this.writableDatabase
        val query = "SELECT * FROM Destino WHERE id_destino = ?"
        val cursor = db.rawQuery(query, arrayOf(idDestino.toString()))

        var destino: Destino? = null
        if (cursor.moveToFirst()) {
            val id_destino = cursor.getInt(cursor.getColumnIndexOrThrow("id_destino"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
            val ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion"))
            val imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
            val id_ciudad = cursor.getInt(cursor.getColumnIndexOrThrow("id_ciudad"))
            val latitud = cursor.getString(cursor.getColumnIndexOrThrow("latitud"))
            val longitud = cursor.getString(cursor.getColumnIndexOrThrow("longitud"))

            destino = Destino(id_destino, nombre, descripcion, ubicacion, imagen, id_ciudad,latitud,longitud)
        }
        cursor.close()

        return destino
    }
    fun obtenerLatitudLongitudPorIdDestino(idDestino: Int): Pair<String, String>? {
        val db = this.readableDatabase
        val query = "SELECT latitud, longitud FROM Destino WHERE id_destino = ?"
        val cursor = db.rawQuery(query, arrayOf(idDestino.toString()))

        var latitudLongitud: Pair<String, String>? = null
        if (cursor.moveToFirst()) {
            val latitud = cursor.getString(cursor.getColumnIndexOrThrow("latitud"))
            val longitud = cursor.getString(cursor.getColumnIndexOrThrow("longitud"))
            latitudLongitud = Pair(latitud, longitud)
        }
        cursor.close()
        db.close()

        return latitudLongitud
    }


    // ----------------------
    // | METODOS DE ITINERARIO |
    // ----------------------

    fun crearItinerario(itinerario: Itinerario) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("nombre", itinerario.nombre)
        cv.put("fecha_inicio", itinerario.fecha_inicio)
        cv.put("fecha_fin", itinerario.fecha_fin)
        cv.put("username", itinerario.username)
        db.insert(TABLE_ITINERARIO, null, cv)
        db.close()
    }

    fun obtenerItinerarios(): ArrayList<Itinerario> {
        val itinerarios = ArrayList<Itinerario>()
        val db = this.readableDatabase
        val query = "SELECT * FROM Itinerario"

        val cursor: Cursor?

        cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val idItinerario = cursor.getInt(cursor.getColumnIndexOrThrow("id_itinerario"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val fechaInicio = cursor.getString(cursor.getColumnIndexOrThrow("fecha_inicio"))
                val fechaFin = cursor.getString(cursor.getColumnIndexOrThrow("fecha_fin"))
                val usernameUsuario = cursor.getString(cursor.getColumnIndexOrThrow("username"))
                //val idUsuario=1

                val itinerario = Itinerario(id_itinerario = idItinerario, nombre = nombre, fecha_inicio = fechaInicio, fecha_fin = fechaFin, username = usernameUsuario)
                itinerarios.add(itinerario)
            } while (cursor.moveToNext())
        }
        println(itinerarios)
        cursor.close()
        db.close()

        return itinerarios
    }

    fun obtenerItinerariosPorUsername(username: String): ArrayList<Itinerario> {
        val itinerarios = ArrayList<Itinerario>()
        val db = this.readableDatabase
        val query = "SELECT * FROM Itinerario WHERE username = '$username'"

        val cursor: Cursor?

        cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val idItinerario = cursor.getInt(cursor.getColumnIndexOrThrow("id_itinerario"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val fechaInicio = cursor.getString(cursor.getColumnIndexOrThrow("fecha_inicio"))
                val fechaFin = cursor.getString(cursor.getColumnIndexOrThrow("fecha_fin"))
                val usernameUsuario = cursor.getString(cursor.getColumnIndexOrThrow("username"))

                val itinerario = Itinerario(id_itinerario = idItinerario, nombre = nombre, fecha_inicio = fechaInicio, fecha_fin = fechaFin, username = usernameUsuario)
                itinerarios.add(itinerario)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return itinerarios

    }
    fun obtenerItinerarioPorId(idItinerario: Int): Itinerario? {
        val db = this.readableDatabase
        val query = "SELECT * FROM Itinerario WHERE id_itinerario = $idItinerario"

        val cursor: Cursor?
        var itinerario: Itinerario? = null

        cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val fechaInicio = cursor.getString(cursor.getColumnIndexOrThrow("fecha_inicio"))
            val fechaFin = cursor.getString(cursor.getColumnIndexOrThrow("fecha_fin"))
            val usernameUsuario = cursor.getString(cursor.getColumnIndexOrThrow("username"))

            itinerario = Itinerario(id_itinerario = idItinerario, nombre = nombre, fecha_inicio = fechaInicio, fecha_fin = fechaFin, username = usernameUsuario)
        }

        cursor.close()
        db.close()

        return itinerario
    }
    fun updateItinerario(itinerario: Itinerario) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("nombre", itinerario.nombre)
        cv.put("fecha_inicio", itinerario.fecha_inicio)
        cv.put("fecha_fin", itinerario.fecha_fin)
        val whereClause = "id_itinerario = ?"
        val whereArgs = arrayOf(itinerario.id_itinerario.toString())
        db.update(TABLE_ITINERARIO, cv, whereClause, whereArgs)
        db.close()
    }
    fun eliminarItinerarioYRegistrosPorId(idItinerario: Int) {
        val db = this.writableDatabase

        // Eliminar los registros asociados al itinerario
        val whereClause = "id_itinerario = ?"
        val whereArgs = arrayOf(idItinerario.toString())
        db.delete(TABLE_REGISTRO, whereClause, whereArgs)

        // Eliminar el itinerario
        db.delete(TABLE_ITINERARIO, "id_itinerario = ?", arrayOf(idItinerario.toString()))

        db.close()
    }





    // ----------------------
    // | METODOS DE REGISTRO |
    // ----------------------


    fun crearRegistro(registro: RegistroDestino) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("id_destino", registro.id_destino)
        cv.put("id_itinerario", registro.id_itinerario)
        cv.put("fecha", registro.fecha)

        db.insert(TABLE_REGISTRO, null, cv)
        db.close()
    }

    fun editarRegistro(registro: RegistroDestino) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("id_destino", registro.id_destino)
        cv.put("id_itinerario", registro.id_itinerario)
        cv.put("fecha", registro.fecha)

        db.update(TABLE_REGISTRO, cv, "id_registro = ?", arrayOf(registro.id_registro.toString()))
        db.close()
    }

    fun eliminarRegistro(idRegistro: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_REGISTRO, "id_registro = ?", arrayOf(idRegistro.toString()))
        db.close()
    }

    fun buscarRegistroPorId(idRegistro: Int): RegistroDestino? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_REGISTRO WHERE id_registro = ?"
        val selectionArgs = arrayOf(idRegistro.toString())
        val cursor = db.rawQuery(query, selectionArgs)

        var registro: RegistroDestino? = null

        if (cursor.moveToFirst()) {
            val idDestino = cursor.getInt(cursor.getColumnIndexOrThrow("id_destino"))
            val idItinerario = cursor.getInt(cursor.getColumnIndexOrThrow("id_itinerario"))
            val fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
            registro = RegistroDestino(idRegistro, idDestino, idItinerario, fecha)
        }

        cursor.close()
        db.close()

        return registro
    }
    /*
    fun obtenerDestinosPorRegistro():ArrayList<RegistroDestino>{
        val registros = ArrayList<RegistroDestino>()
        val db = this.readableDatabase
        val query = "SELECT * FROM RegistroDestino"

        val cursor: Cursor?

        cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val idRegistro = cursor.getInt(cursor.getColumnIndexOrThrow("id_registro"))
                val idDestino = cursor.getInt(cursor.getColumnIndexOrThrow("id_destino"))
                val idItinerario = cursor.getInt(cursor.getColumnIndexOrThrow("id_itinerario"))

                val registro = RegistroDestino(id_registro = idRegistro, id_destino = idDestino, id_itinerario = idItinerario)
                registros.add(registro)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return registros
    }
*/

    fun obtenerNombresDestinoPorRegistroItinerario(idItinerario: Int): List<String> {
        val nombresDestino = mutableListOf<String>()
        val db = this.readableDatabase
        val query = "SELECT d.nombre FROM Destino d " +
                "INNER JOIN RegistroDestino r ON d.id_destino = r.id_destino " +
                "WHERE r.id_itinerario = ?"
        val selectionArgs = arrayOf(idItinerario.toString())
        val cursor = db.rawQuery(query, selectionArgs)

        while (cursor.moveToNext()) {
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            nombresDestino.add(nombre)
        }

        cursor.close()
        db.close()
        return nombresDestino
    }

    fun obtenerDestinosRegistradosPorItinerario(idItinerario: Int): List<Triple<Int, String, String>> {
        val destinosRegistrados = mutableListOf<Triple<Int, String, String>>()
        val db = this.readableDatabase
        val query = "SELECT r.id_registro, d.nombre, r.fecha FROM Destino d " +
                "INNER JOIN RegistroDestino r ON d.id_destino = r.id_destino " +
                "WHERE r.id_itinerario = ?"
        val selectionArgs = arrayOf(idItinerario.toString())
        val cursor = db.rawQuery(query, selectionArgs)

        while (cursor.moveToNext()) {
            val idRegistro = cursor.getInt(cursor.getColumnIndexOrThrow("id_registro"))
            val nombreDestino = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
            val destinoRegistrado = Triple(idRegistro, nombreDestino, fecha)
            destinosRegistrados.add(destinoRegistrado)
        }

        cursor.close()
        db.close()
        return destinosRegistrados
    }

    // -----------------------
    // | METODOS DE FAVORITOS |
    // -----------------------


    fun añadirFavorito(favorito: Favorito) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("username", favorito.username)
        cv.put("id_destino", favorito.id_destino)

        db.insert(TABLE_FAVORITOS, null, cv)
        db.close()
    }

    fun obtenerFavoritosPorUsername(username: String): ArrayList<Destino> {
        val destinos = ArrayList<Destino>()
        val db = this.readableDatabase
        val query = "SELECT Destino.* FROM Favoritos INNER JOIN Destino ON Favoritos.id_destino = Destino.id_destino WHERE Favoritos.username = ?"
        val cursor = db.rawQuery(query, arrayOf(username))

        while (cursor.moveToNext()) {
            val id_destino = cursor.getInt(cursor.getColumnIndexOrThrow("id_destino"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
            val ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion"))
            val imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
            val id_ciudad = cursor.getInt(cursor.getColumnIndexOrThrow("id_ciudad"))
            val latitud = cursor.getString(cursor.getColumnIndexOrThrow("latitud"))
            val longitud = cursor.getString(cursor.getColumnIndexOrThrow("longitud"))

            destinos.add(Destino(id_destino, nombre, descripcion, ubicacion, imagen, id_ciudad, latitud,longitud))
        }

        cursor.close()
        db.close()

        return destinos
    }

    fun eliminarFavorito(favorito: Favorito) {
        val db = this.writableDatabase
        val whereClause = "username = ? AND id_destino = ?"
        val whereArgs = arrayOf(favorito.username, favorito.id_destino.toString())
        db.delete(TABLE_FAVORITOS, whereClause, whereArgs)
        db.close()
    }
    // -----------------------
    // | METODOS DE RESENA\ |
    //

    fun crearResena(resena: Resena) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("descripcion", resena.descripcion)
        cv.put("fecha", resena.fecha)
        cv.put("id_destino", resena.id_destino)
        cv.put("username", resena.username)
        db.insert(TABLE_RESENA, null, cv)
        db.close()
    }


    fun obtenerResenas(): ArrayList<Resena> {
        val resenas = ArrayList<Resena>()
        val db = this.readableDatabase
        val query = "SELECT * FROM Resena"

        val cursor: Cursor?

        cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val idResena = cursor.getInt(cursor.getColumnIndexOrThrow("id_resena"))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
                val idDestino = cursor.getInt(cursor.getColumnIndexOrThrow("id_destino"))
                val username = cursor.getString(cursor.getColumnIndexOrThrow("username"))

                val resena = Resena(id_resena=idResena, descripcion = descripcion, fecha = fecha, id_destino = idDestino, username = username)
                resenas.add(resena)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return resenas
    }



}
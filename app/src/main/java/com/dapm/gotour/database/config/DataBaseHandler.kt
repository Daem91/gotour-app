package com.dapm.gotour.database.config

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.preference.PreferenceManager
import android.widget.Toast
import com.dapm.gotour.database.model.Ciudad
import com.dapm.gotour.database.model.Destino
import com.dapm.gotour.database.model.Itinerario
import com.dapm.gotour.database.model.Usuario
import java.lang.Exception

val DATABASE_NAME = "GoTout_DB"

val COL_USERNAME = "username"
val COL_PASS = "password"
val TABLE_USUARIO = "Usuario"
val TABLE_CIUDAD = "Ciudad"
val TABLE_DESTINO = "Destino"
val TABLE_ITINERARIO = "Itinerario"

val KEY_CIUDAD = "id_ciudad"
val NAME_CIUDAD = "nombre"
val DEPARTAMENTO_CIUDAD = "departamento"

class DataBaseHandler(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createUser = "CREATE TABLE Usuario(username TEXT PRIMARY KEY, password TEXT)";
        val createCity = "CREATE TABLE Ciudad(id_ciudad INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, departamento TEXT, imagen TEXT)";
        val createDestination = "CREATE TABLE Destino(id_destino INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, ubicacion TEXT, imagen TEXT, id_ciudad INTEGER, FOREIGN KEY(id_ciudad) REFERENCES Ciudad(id_ciudad))"
        val createItinerario = "CREATE TABLE Itinerario(id_itinerario INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, fecha_inicio TEXT, fecha_fin TEXT,  username TEXT, FOREIGN KEY(username) REFERENCES Usuario(username))"

        db?.execSQL(createUser)
        db?.execSQL(createCity)
        db?.execSQL(createDestination)
        db?.execSQL(createItinerario)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CIUDAD")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_DESTINO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_DESTINO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ITINERARIO")


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
            destinos.add(Destino(id_destino, nombre, descripcion, ubicacion, imagen, idCiudad))
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

            destino = Destino(id_destino, nombre, descripcion, ubicacion, imagen, id_ciudad)
        }
        cursor.close()

        return destino
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






}
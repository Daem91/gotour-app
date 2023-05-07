package com.dapm.gotour.database.config

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.dapm.gotour.database.model.Usuario

val DATABASE_NAME = "GoTout_DB"
val TABLE_NAME = "Usuario"
val COL_USERNAME = "username"
val COL_PASS = "password"

class DataBaseHandler(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_USERNAME + " TEXT PRIMARY KEY," +
                COL_PASS + " TEXT)";

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun createUsuario(usuario: Usuario): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_USERNAME, usuario.username)
        cv.put(COL_PASS, usuario.contrasena)
        val resultado = db.insert(TABLE_NAME, null, cv)

        if (resultado == (-1).toLong()){
            return false
        } else {
            return true
        }
    }

    fun comprobarUsuario(usuario: Usuario): Boolean {
        val username = usuario.username
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

}
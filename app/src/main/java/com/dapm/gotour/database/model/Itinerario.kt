package com.dapm.gotour.database.model

import java.util.*

class Itinerario {
    var id_itinerario: Int? = null
    var nombre: String = ""
    var fecha_inicio: String = ""
    var fecha_fin: String = ""
    var username: String = ""
    //var id_usuario: Int = 0

    constructor(
        id_itinerario: Int?,
        nombre: String,
        fecha_inicio: String,
        fecha_fin: String,
        username: String,
        //id_usuario: Int
    ) {
        this.id_itinerario = id_itinerario
        this.nombre = nombre
        this.fecha_inicio = fecha_inicio
        this.fecha_fin = fecha_fin
        this.username = username
        //this.id_usuario = id_usuario
    }
}
package com.dapm.gotour.database.model

import java.util.*

class Itinerario {
    var id_itinerario: Int? = null
    var nombre: String = ""
    var fecha_inicio: String = ""
    var fecha_fin: String = ""
    var username: String = ""


    constructor(
        id_itinerario: Int?,
        nombre: String,
        fecha_inicio: String,
        fecha_fin: String,
        username: String,

    ) {
        this.id_itinerario = id_itinerario
        this.nombre = nombre
        this.fecha_inicio = fecha_inicio
        this.fecha_fin = fecha_fin
        this.username = username

    }
    constructor(
        id_itinerario: Int?,
        nombre: String,
        fecha_inicio: String,
        fecha_fin: String,


        ) {
        this.id_itinerario = id_itinerario
        this.nombre = nombre
        this.fecha_inicio = fecha_inicio
        this.fecha_fin = fecha_fin


    }
}
package com.dapm.gotour.database.model

class Resena {
    var id_resena: Int? = null
    var descripcion: String = ""
    var id_destino: Int? = null
    var username: String? = null
    var fecha: String = ""

    constructor(
        id_resena: Int?,
        descripcion: String,
        id_destino: Int?,
        username: String?,
        fecha: String
    ) {
        this.id_resena = id_resena
        this.descripcion = descripcion
        this.id_destino = id_destino
        this.username = username
        this.fecha = fecha
    }
}
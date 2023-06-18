package com.dapm.gotour.database.model

class Destino {

    var id_destino: Int? = null
    var nombre: String = ""
    var descripcion: String = ""
    var ubicacion: String = ""
    var imagen: String = ""
    var id_ciudad: Int = 0
    var latitud: String = ""
    var longitud: String = ""

    constructor(
        id_destino: Int?,
        nombre: String,
        descripcion: String,
        ubicacion: String,
        imagen: String,
        id_ciudad: Int,
        latitud: String,
        longitud: String
    ) {
        this.id_destino = id_destino
        this.nombre = nombre
        this.descripcion = descripcion
        this.ubicacion = ubicacion
        this.imagen = imagen
        this.id_ciudad = id_ciudad
        this.latitud = latitud
        this.longitud = longitud
    }
}
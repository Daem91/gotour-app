package com.dapm.gotour.database.model

class Ciudad {

    var id_ciudad: Int? = null
    var nombre: String = ""
    var departamento: String = ""
    var imagen: String = ""

    constructor(
        id_ciudad: Int?, nombre: String, departamento: String, imagen: String
    ) {
        this.id_ciudad = id_ciudad
        this.nombre = nombre
        this.departamento = departamento
        this.imagen = imagen
    }

    constructor()


}
package com.dapm.gotour.database.model

class RegistroDestino {
    var id_registro:Int?=null
    var id_destino: Int? = null
    var id_itinerario: Int? = null
    var fecha:String=""

    constructor(id_registro: Int?, id_destino: Int?, id_itinerario: Int?, fecha: String) {
        this.id_registro = id_registro
        this.id_destino = id_destino
        this.id_itinerario = id_itinerario
        this.fecha = fecha
    }
}
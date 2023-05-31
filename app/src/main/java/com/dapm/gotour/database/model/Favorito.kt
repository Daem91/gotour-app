package com.dapm.gotour.database.model

class Favorito {
    var id_favorito: Int? = null
    var username: String? = null
    var id_destino: Int? = null

    constructor(
        id_favorito: Int?, username: String, id_destino: Int
    ) {
        this.id_favorito = id_favorito
        this.username = username
        this.id_destino = id_destino
    }

}
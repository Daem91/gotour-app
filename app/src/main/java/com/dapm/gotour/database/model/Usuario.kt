package com.dapm.gotour.database.model

class Usuario {

    var username: String = ""
    var contrasena: String = ""

    constructor(
        username: String, contrasena: String
    ) {
        this.username = username
        this.contrasena = contrasena
    }

    constructor()

}


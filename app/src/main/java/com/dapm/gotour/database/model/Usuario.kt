package com.dapm.gotour.database.model

class Usuario {
    companion object{
        var username: String = ""
    }

    var contrasena: String = ""

    constructor(
        username: String, contrasena: String
    ) {
        Companion.username = username
        this.contrasena = contrasena
    }

    constructor()

}


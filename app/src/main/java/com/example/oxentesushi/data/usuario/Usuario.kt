package com.example.oxentesushi.data.usuario

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var nome: String,
    var login: String,
    var senha: String,
    var endereco: String,
    var telefone: String,
){
    constructor() : this(null, "", "", "", "","")
}
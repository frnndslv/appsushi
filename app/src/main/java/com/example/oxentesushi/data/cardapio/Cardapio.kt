package com.example.oxentesushi.data.cardapio

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Cardapio(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var nome: String,
){
    constructor() : this(null, "")
}
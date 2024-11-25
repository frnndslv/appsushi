package com.example.oxentesushi.data.cardapio

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Cardapio(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var nome: String,
    var img: String,
    var valor: String,
){
    constructor() : this(null, "", "", "")

    val imgUri: Uri
        get() = Uri.parse(img)
}
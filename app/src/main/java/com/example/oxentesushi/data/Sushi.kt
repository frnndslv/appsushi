package com.example.oxentesushi.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sushis")
data class Sushi(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val isCompleted: Boolean = false
    //Professor o boolean ta aqui por causa da versão, mano q dificil atualizar isso mdss
    //juro q tentei, acabei criando ele ai na primeira versão, to só o meme do pinguim desesperado
    //enfim deixa ele ai como pet
)
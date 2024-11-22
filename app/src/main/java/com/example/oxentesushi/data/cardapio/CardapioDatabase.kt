package com.example.oxentesushi.data.cardapio

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cardapio::class], version = 1)
abstract class CardapioDatabase: RoomDatabase(){

    abstract fun cardapioDao(): CardapioDAO

    companion object{
        fun abrirCardapioBancoDeDados(context: Context): CardapioDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                CardapioDatabase::class.java, "cardapio.db"
            ).build()
        }
    }
}

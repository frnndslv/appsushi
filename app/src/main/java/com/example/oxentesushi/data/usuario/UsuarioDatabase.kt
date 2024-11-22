package com.example.oxentesushi.data.usuario

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Usuario::class], version = 1)
abstract class UsuarioDatabase: RoomDatabase(){

    abstract fun usuarioDao(): UsuarioDAO

    companion object{
        fun abrirBancoDeDados(context: Context): UsuarioDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                UsuarioDatabase::class.java, "usuario.db"
            ).build()
        }
    }
}

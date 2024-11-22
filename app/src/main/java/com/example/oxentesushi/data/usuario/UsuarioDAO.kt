package com.example.oxentesushi.data.usuario

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.oxentesushi.data.usuario.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDAO{

    @Query("select * from usuario")
    fun listarUsuarios(): Flow<List<Usuario>>

    @Query("select * from usuario where id = :idx")
    suspend fun buscarUsuarioId(idx: Int): Usuario

    @Upsert
    suspend fun gravarUsuario(usuario: Usuario)

    @Query("SELECT CASE WHEN EXISTS (\n" +
            "    SELECT *\n" +
            "    FROM [usuario]\n" +
            "    WHERE login = :usuario\n" +
            "    AND  senha = :senha\n" +
            ")\n" +
            "THEN CAST(1 AS BIT)\n" +
            "ELSE CAST(0 AS BIT) END")
    suspend fun buscarUsuarioESenha(usuario: String, senha: String): Boolean

    @Delete
    suspend fun excluirUsuario(usuario: Usuario)

}
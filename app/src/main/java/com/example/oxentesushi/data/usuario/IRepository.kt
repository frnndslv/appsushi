package com.example.oxentesushi.data.usuario

import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun listarUsuarios(): Flow<List<Usuario>>
    suspend fun buscarUsuarioId(idx: Int): Usuario?
    suspend fun gravarUsuario(usuario: Usuario)
    suspend fun excluirUsuario(usuario: Usuario)
    suspend fun buscarUsuarioESenha(usuario:String, senha:String): Boolean
}

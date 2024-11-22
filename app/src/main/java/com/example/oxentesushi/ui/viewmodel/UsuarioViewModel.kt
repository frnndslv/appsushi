package com.example.oxentesushi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oxentesushi.data.usuario.IRepository
import com.example.oxentesushi.data.usuario.Usuario

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UsuarioViewModel(private val repository : IRepository) : ViewModel() {
    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> get() = _usuarios

    init {
        viewModelScope.launch {
            repository.listarUsuarios().collectLatest { listaDeUsuarios ->
                _usuarios.value = listaDeUsuarios
            }
        }
    }

    fun excluir(usuario: Usuario) {
        viewModelScope.launch {
            repository.excluirUsuario(usuario)
        }
    }

    suspend fun buscarPorId(usuarioId: Int): Usuario? {
        return withContext(Dispatchers.IO){
            repository.buscarUsuarioId(usuarioId)
        }
    }

    suspend fun buscarPorLoginESenha(usuario: String, senha: String): Boolean {
        return withContext(Dispatchers.Main){
            repository.buscarUsuarioESenha(usuario, senha)
        }
    }

    fun gravar(usuario: Usuario) {
        viewModelScope.launch {
            repository.gravarUsuario(usuario)
        }
    }
}
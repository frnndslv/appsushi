package com.example.oxentesushi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oxentesushi.data.cardapio.Cardapio
import com.example.oxentesushi.data.cardapio.ICardapioRepository
import com.example.oxentesushi.data.usuario.Usuario

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CardapioViewModel(private val repository : ICardapioRepository) : ViewModel() {
    private val _cardapios = MutableStateFlow<List<Cardapio>>(emptyList())
    val cardapios: StateFlow<List<Cardapio>> get() = _cardapios

    init {
        viewModelScope.launch {
            repository.listarCardapios().collectLatest { listaDeCardapios ->
                _cardapios.value = listaDeCardapios
            }
        }
    }

    fun excluir(cardapio: Cardapio) {
        viewModelScope.launch {
            repository.excluirCardapio(cardapio)
        }
    }

    suspend fun buscarPorId(cardapioId: Int): Cardapio? {
        return withContext(Dispatchers.IO){
            repository.buscarCardapioId(cardapioId)
        }
    }

    fun gravar(cardapio: Cardapio) {
        viewModelScope.launch {
            repository.gravarCardapio(cardapio)
        }
    }

}
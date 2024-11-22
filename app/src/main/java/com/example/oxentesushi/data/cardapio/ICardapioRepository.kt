package com.example.oxentesushi.data.cardapio
import kotlinx.coroutines.flow.Flow

interface ICardapioRepository {
    fun listarCardapios(): Flow<List<Cardapio>>
    suspend fun buscarCardapioId(idx: Int): Cardapio?
    suspend fun gravarCardapio(cardapio: Cardapio)
    suspend fun excluirCardapio(cardapio: Cardapio)
}

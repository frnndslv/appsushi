package com.example.oxentesushi.data.cardapio

import kotlinx.coroutines.flow.Flow

class LocalCardapioRepository(
    private val dao : CardapioDAO
) : ICardapioRepository {

    override fun listarCardapios(): Flow<List<Cardapio>> {
        return dao.listarCardapios()
    }

    override suspend fun buscarCardapioId(idx: Int): Cardapio {
        return dao.buscarCardapioId(idx)
    }

    override suspend fun gravarCardapio(cardapio: Cardapio) {
        dao.gravarCardapio(cardapio)
    }

    override suspend fun excluirCardapio(cardapio: Cardapio) {
        dao.excluirCardapio(cardapio)
    }

}

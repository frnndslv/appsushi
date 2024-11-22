package com.example.oxentesushi.data.cardapio

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CardapioDAO{

    @Query("select * from cardapio")
    fun listarCardapios(): Flow<List<Cardapio>>

    @Query("select * from cardapio where id = :idx")
    suspend fun buscarCardapioId(idx: Int): Cardapio

    @Upsert
    suspend fun gravarCardapio(cardapio: Cardapio)

    @Delete
    suspend fun excluirCardapio(cardapio: Cardapio)

}
package com.example.oxentesushi.data.cardapio

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RemoteCardapioRepository() : ICardapioRepository {

    var db = FirebaseFirestore.getInstance()
    var cardapioCollection = db.collection("cardapios")

    override fun listarCardapios(): Flow<List<Cardapio>> = callbackFlow {
        val listener = cardapioCollection.addSnapshotListener {
                dados, erros ->
            if (erros != null){
                close(erros)
                return@addSnapshotListener
            }
            if (dados != null){
                val cardapios = dados.documents.mapNotNull { dado ->
                    dado.toObject(Cardapio::class.java)
                }
                trySend(cardapios).isSuccess
            }
        }
        awaitClose{ listener.remove()}
    }

    suspend fun getId(): Int{
        val dados = cardapioCollection.get().await()

        val maxId = dados.documents.mapNotNull {
            it.getLong("id")?.toInt()
        }.maxOrNull() ?: 0
        return maxId + 1
    }

    override suspend fun gravarCardapio(cardapio: Cardapio) {
        val document: DocumentReference
        if (cardapio.id == null){ // Inclusão
            cardapio.id = getId()
            document = cardapioCollection.document(cardapio.id.toString())
        } else { // Alteração
            document = cardapioCollection.document(cardapio.id.toString())
        }
        document.set(cardapio).await()
    }

    override suspend fun buscarCardapioId(idx: Int): Cardapio? {
        val dados = cardapioCollection.document(idx.toString()).get().await()

        return dados.toObject(Cardapio::class.java)
    }

    override suspend fun excluirCardapio(cardapio: Cardapio) {
        cardapioCollection.document(cardapio.id.toString()).delete().await()
    }
}

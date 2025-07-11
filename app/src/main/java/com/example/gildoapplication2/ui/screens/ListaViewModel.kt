package com.example.gildoapplication2.ui.screens

import NotificationWorker
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.gildoapplication2.ItemDeLinha
import android.content.Context
import androidx.work.*
import java.util.Calendar
import java.util.concurrent.TimeUnit

open class ListaViewModel : ViewModel() {
    val itens = mutableStateListOf<ItemDeLinha>()
    private var idCounter = 1
    fun adicionarItem(texto1: String, horario: String) {
        itens.add(ItemDeLinha(idCounter++, texto1, horario))
    }

    fun removerItem(item: ItemDeLinha) {
        itens.remove(item)
    }

    fun agendarNotificacao(context: Context, nome: String, horario: String) {
        val partes = horario.split(":")
        if (partes.size != 2) return

        val hora = partes[0].toIntOrNull() ?: return
        val minuto = partes[1].toIntOrNull() ?: return

        // Calcular tempo até o horário
        val agora = Calendar.getInstance()
        val horarioAlvo = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hora)
            set(Calendar.MINUTE, minuto)
            set(Calendar.SECOND, 0)
            if (before(agora)) add(Calendar.DAY_OF_MONTH, 1) // se passou, agenda para o dia seguinte
        }

        val delay = horarioAlvo.timeInMillis - agora.timeInMillis

        val dados = Data.Builder()
            .putString("nome", nome)
            .build()

        val requisicao = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(dados)
            .build()

        WorkManager.getInstance(context).enqueue(requisicao)
    }

}
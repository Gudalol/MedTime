package com.example.gildoapplication2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class ItemDeLinha(
    val id: Int,
    val texto1: String,
    val horario: String,
){
    var marcado by mutableStateOf(false)
}

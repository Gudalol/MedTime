package com.example.gildoapplication2.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gildoapplication2.ItemDeLinha
import com.example.gildoapplication2.R

class TelaPrincipal {

    @Composable
    fun LinhaItem(
        item: ItemDeLinha,
        onCheckChange: (Boolean) -> Unit,
        onExcluir: () -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Checkbox(
                checked = item.marcado,
                onCheckedChange = {novoValor -> item.marcado = novoValor}
            )

            // Nome e horário lado a lado
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = item.texto1)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.horario,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            IconButton(onClick = onExcluir) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                    contentDescription = "Excluir"
                )
            }
        }
    }

    @SuppressLint("NotConstructor")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TelaPrincipal(
        viewModel: ListaViewModel,
        onIrParaCriar: () -> Unit
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Medication",
                            color = Color.White,
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif
                            )
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF1A2A4B)
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onIrParaCriar,
                    containerColor = Color(0xFF1A2A4B),
                    contentColor = Color.White
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_add_24),
                        contentDescription = "Adicionar item"
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                LazyColumn {
                    items(viewModel.itens, key = { it.id }) { item ->
                        LinhaItem(
                            item = item,
                            onCheckChange = { novoValor -> item.marcado = novoValor },
                            onExcluir = { viewModel.removerItem(item) }
                        )
                        Divider(color = Color.LightGray, thickness = 1.dp)
                    }
                }
            }
        }
    }

    // 👇 Preview visual
    @Preview(showBackground = true)
    @Composable
    fun TelaPrincipalPreview() {
        val viewModelFake = @SuppressLint("StaticFieldLeak")
        object : ListaViewModel() {
            init {
                itens.add(ItemDeLinha(1, "Paracetamol", "19:00"))
                itens.add(ItemDeLinha(2, "Ibuprofeno", "20:00"))
            }
        }

        TelaPrincipal(viewModelFake, onIrParaCriar = {})
    }
}

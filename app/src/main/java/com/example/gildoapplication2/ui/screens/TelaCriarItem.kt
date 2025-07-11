package com.example.gildoapplication2.ui.screens

import android.annotation.SuppressLint
import android.widget.EditText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.addTextChangedListener
import androidx.compose.ui.platform.LocalContext


class TelaCriarItem {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TelaComTopAppBar(
        viewModel: ListaViewModel,
        onVoltar: () -> Unit
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Add Medication",
                            color = Color.White,
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif
                            )
                        )
                    },
                    colors = topAppBarColors(
                        containerColor = Color(0xFF1A2A4B)
                    )
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TelaCriarItem(viewModel = viewModel, onVoltar = onVoltar)
            }
        }
    }

    @Composable
    fun EditTextCompose(
        text: String,
        hint: String,
        onTextChange: (String) -> Unit,
        modifier: Modifier = Modifier
    ){
        AndroidView(
            factory = {context ->
                EditText(context).apply {
                    setText(text)
                    setHint(hint)
                    textSize = 16f
                }
            },
            modifier = modifier,
            update = { editText ->
                if(editText.text.toString() != text){
                    editText.setText(text)
                    editText.setSelection(text.length)
                }
                editText.addTextChangedListener{
                    onTextChange(it.toString())
                }
            }
        )
    }
    @Composable
    fun EditTextHorario(
        text: String,
        onTextChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        hint: String = ""
    ) {
        AndroidView(
            factory = { context ->
                EditText(context).apply {
                    setText(text)
                    setHint(hint)
                    textSize = 16f
                    inputType = android.text.InputType.TYPE_CLASS_NUMBER

                    addTextChangedListener(object : android.text.TextWatcher {
                        var isUpdating = false
                        var oldText = ""

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                        override fun afterTextChanged(s: android.text.Editable?) {
                            if (isUpdating) return

                            var str = s.toString()
                            // Remove tudo que não é número
                            val digits = str.filter { it.isDigit() }

                            var formatted = ""
                            if (digits.length >= 1) {
                                // Pega até 2 primeiros dígitos (hora)
                                formatted += digits.take(2)
                                if (digits.length >= 3) {
                                    // Se tem pelo menos 3 dígitos, adiciona ':' e os dois últimos (minuto)
                                    formatted += ":${digits.substring(2, Math.min(4, digits.length))}"
                                } else if (digits.length > 2) {
                                    formatted += ":"
                                }
                            }

                            // Limita a 5 caracteres (ex: "23:59")
                            if (formatted.length > 5) {
                                formatted = formatted.take(5)
                            }

                            isUpdating = true
                            if (formatted != str) {
                                setText(formatted)
                                setSelection(formatted.length)
                            }
                            isUpdating = false

                            // Passa só os números limpos (sem ":") para o estado
                            onTextChange(formatted)
                        }
                    })
                }
            },
            modifier = modifier,
            update = { editText ->
                if (editText.text.toString() != text) {
                    editText.setText(text)
                    editText.setSelection(text.length)
                }
            }
        )
    }

    @SuppressLint("NotConstructor")
    @Composable
    fun TelaCriarItem(
        viewModel: ListaViewModel,
        onVoltar: () -> Unit
    ) {
        var context = LocalContext.current
        var texto by remember { mutableStateOf("") }
        var horario by remember { mutableStateOf("") }

        var erroTexto by remember { mutableStateOf("") }
        var erroHorario by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(19.dp)
        ) {
            Text(text = "Nome", fontSize = 16.sp, color = Color.DarkGray)

            EditTextCompose(
                text = texto,
                onTextChange = {
                    texto = it
                    erroTexto = ""
                },
                hint = "Exemplo: Ibuprofeno",
                modifier = Modifier.fillMaxWidth()
            )

            if (erroTexto.isNotEmpty()) {
                Text(erroTexto, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Horário", fontSize = 16.sp, color = Color.DarkGray)

            EditTextHorario(
                text = horario,
                onTextChange = {
                    horario = it
                    erroHorario = ""
                },
                hint = "Exemplo: 20:00",
                modifier = Modifier.fillMaxWidth()
            )

            if (erroHorario.isNotEmpty()) {
                Text(erroHorario, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    var valido = true

                    if (texto.isBlank()) {
                        erroTexto = "O nome não pode estar vazio"
                        valido = false
                    }

                    if (horario.isBlank()) {
                        erroHorario = "O horário não pode estar vazio"
                        valido = false
                    } else if (!horarioValido(horario)) {
                        erroHorario = "Horário inválido (use formato HH:mm)"
                        valido = false
                    }

                    if (valido) {
                        viewModel.adicionarItem(texto, horario)
                        viewModel.agendarNotificacao(context = context, nome = texto, horario = horario)
                        onVoltar()
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1A2A4B),
                    contentColor = Color.White
                )
            ) {
                Text("Confirmar")
            }
        }
    }

    fun horarioValido(horario: String): Boolean {
        val partes = horario.split(":")
        if (partes.size != 2) return false

        val hora = partes[0].toIntOrNull() ?: return false
        val minuto = partes[1].toIntOrNull() ?: return false

        return hora in 0..23 && minuto in 0..59
    }

    @Preview(showBackground = true)
    @Composable
    fun TelaCriarItemPreview() {
        // Cria um viewModel fake só para visualização (não precisa funcionar)
        val viewModelFake = object : ListaViewModel() {}
        TelaCriarItem(viewModelFake) { }
    }
}
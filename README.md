Este projeto é um aplicativo Android simples desenvolvido com **Jetpack Compose**, com o objetivo de ajudar usuários a **registrar, acompanhar e receber notificações de seus medicamentos**.

## 📱 Funcionalidades

- ✅ Cadastro de medicamentos com nome e horário.
- ⏰ Formato de horário automático (`HH:mm`) com validação.
- 🔔 Notificações automáticas no horário agendado.
- ✔️ Marcação de medicamentos tomados com checkbox.
- 💾 Dados controlados por uma ViewModel com estado reativo.

## 🧠 Tecnologias Utilizadas

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) para agendamento de notificações.
- Material 3 (`androidx.material3`)
- Navegação com `NavHostController`

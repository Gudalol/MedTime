import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.gildoapplication2.R
import java.util.Random

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val nomeRemedio = inputData.getString("nome") ?: "Medicamento"
        val notificacaoManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val canalId = "canal_remedios"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(canalId, "Notificações de Remédio", NotificationManager.IMPORTANCE_HIGH)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificacaoManager.createNotificationChannel(canal)
            }
        }

        val notificacao =
            NotificationCompat.Builder(applicationContext, canalId)
            .setContentTitle("Hora do remédio")
            .setContentText("Tomar: $nomeRemedio")
            .setSmallIcon(R.drawable.pill_icon_icons_com_53621) // Substitua por seu ícone
            .setAutoCancel(true)
            .build()

        notificacaoManager.notify(Random().nextInt(), notificacao)

        return Result.success()
    }
}

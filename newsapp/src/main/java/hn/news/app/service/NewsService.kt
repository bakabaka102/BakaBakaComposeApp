package hn.news.app.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.hn.libs.INewsService
import com.hn.libs.IResultCallback
import com.hn.libs.RequestData
import hn.news.app.OverlayDialogActivity
import hn.news.app.R
import hn.news.app.data.SingleToast

class NewsService : Service() {

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "news_service"
            val channel =
                NotificationChannel(channelId, "News Service", NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)

            /*val notification = NotificationCompat.Builder(this, channelId)
                .setContentTitle("News Service")
                .setContentText("Đang chờ yêu cầu...")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build()
            startForeground(1, notification).also {
                Log.d("TAG", "NewsService - startForeground")
            }*/
        }
    }

    private val binder = object : INewsService.Stub() {
        override fun showLayout(title: String) {
            Handler(Looper.getMainLooper()).post {
                SingleToast.show(applicationContext, message = "Yêu cầu từ App A")
            }
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                // Android 10 trở xuống: Được phép start Activity trực tiếp
                val intent = Intent(applicationContext, OverlayDialogActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    putExtra("title", title)
                }
                startActivity(intent).also {
                    Log.d("TAG", "Start activity trực tiếp (API <= 29)")
                }
            } else {
                // Android 11 trở lên: Chỉ được phép mở Activity khi người dùng click notification
                val intent = Intent(applicationContext, OverlayDialogActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    putExtra("title", title)
                }
                val pendingIntent = PendingIntent.getActivity(
                    applicationContext, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                val notification = NotificationCompat.Builder(this@NewsService, "news_service")
                    .setContentTitle("Yêu cầu từ App A")
                    .setContentText(title)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()

                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(2, notification)
                Log.d("TAG", "Gửi notification, chờ người dùng click (API > 29)")
            }
        }

        override fun showLayoutDialog(
            request: RequestData?,
            resultCallback: IResultCallback?
        ) {

        }
    }

    override fun onBind(intent: Intent?): IBinder = binder
}

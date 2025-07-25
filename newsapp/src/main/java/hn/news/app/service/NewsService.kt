package hn.news.app.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import com.hn.libs.INewsService
import com.hn.libs.IResultCallback
import com.hn.libs.RequestData
import hn.news.app.OverlayDialogActivity
import hn.news.app.data.SingleToast

class NewsService : Service() {
    private val binder = object : INewsService.Stub() {
        override fun showLayout(title: String) {
            Handler(Looper.getMainLooper()).post {
                SingleToast.show(applicationContext, message = "Yêu cầu từ App A")
            }
            val intent = Intent(applicationContext, OverlayDialogActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Bắt buộc khi gọi từ Service
                putExtra("title", title)
                //putExtra("requestData", request) // Parcelable
            }
            startActivity(intent)
        }

        override fun showLayoutDialog(
            request: RequestData?,
            resultCallback: IResultCallback?
        ) {

        }
    }

    override fun onBind(intent: Intent?): IBinder = binder
}

package hn.news.app.data

import android.content.Context
import android.widget.Toast
import okio.Timeout

object SingleToast {

    private var toast: Toast? = null

    fun show(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        toast?.cancel()
        toast = Toast.makeText(context.applicationContext, message, duration)
        toast?.show()
    }
}
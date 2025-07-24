package com.baka.composeapp.service

import android.content.*
import android.os.IBinder
import com.hn.libs.INewsService
import com.hn.libs.RemoteConstants

class NewsRemoteManager(val context: Context) {
    private var newsService: INewsService? = null
    private var isBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            newsService = INewsService.Stub.asInterface(service)
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            newsService = null
            isBound = false
        }
    }

    fun bindService() {
        val intent = Intent(RemoteConstants.NEWS_APP_ACTION)
        intent.setPackage("hn.news.app") // package của newsapp
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    fun unbindService() {
        if (isBound) {
            context.unbindService(serviceConnection)
            isBound = false
        }
    }

    fun showRemoteDialog() {
        newsService?.showLayout("Name")
    }
}

package hn.single.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
//class NetworkStatusTracker @Inject constructor(@ApplicationContext private val context: Context) {
class NetworkStatusTracker (private val context: Context) {

    private val connectivityManager =
        /*context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager*/
        ContextCompat.getSystemService(context, ConnectivityManager::class.java)
    val networkStatus = callbackFlow {
        val callBack = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(false)
            }
        }
        val request = NetworkRequest.Builder().build()
        connectivityManager?.registerNetworkCallback(request, callBack)
        trySend(isConnected())
        awaitClose {
            connectivityManager?.unregisterNetworkCallback(callBack)
        }
    }.distinctUntilChanged()

    private fun isConnected(): Boolean {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                val activeNetwork = connectivityManager?.activeNetwork ?: return false
                val networkCapabilities =
                    connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
                when {
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }

            else -> {
                // Use depreciated methods only on older devices
                val activeNetworkInfo = connectivityManager?.activeNetworkInfo ?: return false
                activeNetworkInfo.isConnected
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        if (!actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            // Cellular - Off
            Log.d("Network", "Cellular is off")
            return false
        }
        if (!actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            // Ethernet Off
            Log.d("Network", "Ethernet is off")
            if (!actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                // Wi-Fi Off
                Log.d("Network", "Wi-Fi is off")
                return true
            }
            // Wi-Fi in On
            if (!actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                // Wi-Fi Not Connected
                Log.d("Network", "Wi-Fi not connected")
                return true
            }
        }
        return false
    }
}
package hn.single.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Repository to monitor internet connectivity status.
 * https://proandroiddev.com/avoid-redundant-network-checks-in-android-smart-offline-aware-api-handling-cd235b0fdf20
 */
class InternetAvailabilityRepository @Inject constructor(networkStatusTracker: NetworkStatusTracker) {

    private val _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> get() = _isConnected

    init {
        CoroutineScope(Dispatchers.Default).launch {
            networkStatusTracker.networkStatus.collect { isConnected ->
                _isConnected.value = isConnected
            }
        }
    }
}
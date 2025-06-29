package hn.single.network

sealed interface UIState<out T> {
    data class Success<T>(val data: T) : UIState<T>
    data class Failure<T>(val throwable: Throwable, val data: T? = null) : UIState<T>
    data object Loading : UIState<Nothing>
}

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>()
}
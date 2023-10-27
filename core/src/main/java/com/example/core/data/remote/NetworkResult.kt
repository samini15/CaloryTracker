package com.example.core.data.remote

/**
 * Use this class to parse response from an API
 */
sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T): NetworkResult<T>(data)
    class Error<T>(message: String, data: T? = null): NetworkResult<T>(data, message)
}

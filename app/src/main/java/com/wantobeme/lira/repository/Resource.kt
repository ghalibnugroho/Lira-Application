package com.wantobeme.lira.repository

sealed class Resource<T>(val response: T? = null, val message: String? = null) {
    class Loading<T>() : Resource<T>()
    class Error<T>(message: String) : Resource<T>(message = message)
    class Success<T>(data: T?) : Resource<T>(response = data)

}
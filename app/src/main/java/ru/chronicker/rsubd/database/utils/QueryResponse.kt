package ru.chronicker.rsubd.database.utils

interface QueryResponse<T> {
    fun onSuccess(data: T)
    fun onError(message: String)
}
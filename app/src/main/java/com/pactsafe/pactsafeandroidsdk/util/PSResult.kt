package com.pactsafe.pactsafeandroidsdk.util

sealed class PSResult<out T> {
    data class Value<out T>(val value: T) : PSResult<T>()
    data class Error(val error: Any) : PSResult<Nothing>()

    companion object {
        fun <T> of(value: T): Value<T> = Value(value)
        fun error(error: Any) = Error(error)
    }
}
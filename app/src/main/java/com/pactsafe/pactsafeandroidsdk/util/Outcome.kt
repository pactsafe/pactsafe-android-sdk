package com.pactsafe.pactsafeandroidsdk.util

sealed class Outcome<out T> {
    data class Value<out T>(val value: T) : Outcome<T>()
    data class Error(val error: Any) : Outcome<Nothing>()

    companion object {
        fun <T> of(value: T): Value<T> = Value(value)
        fun error(error: Any) = Error(error)
    }
}
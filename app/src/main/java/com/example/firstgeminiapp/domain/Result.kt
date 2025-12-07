package com.example.firstgeminiapp.domain

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T> ()
    data class Failure<out T>(val error: T) : Result<T>()
}
package com.example.firstgeminiapp.domain

import com.example.firstgeminiapp.domain.model.Summary

sealed class Result<Summary> {
    data class Sucesss(val data: Summary) : Result<Summary> ()
    data class Failure(val error: Summary) : Result<Summary>()
}
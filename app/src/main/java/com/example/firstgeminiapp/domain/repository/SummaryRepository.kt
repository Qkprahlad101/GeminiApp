package com.example.firstgeminiapp.domain.repository

import com.example.firstgeminiapp.domain.model.Summary
import com.example.firstgeminiapp.domain.Result

interface SummaryRepository {
    suspend fun getSummary(inputText: String) : Result<Summary>
}
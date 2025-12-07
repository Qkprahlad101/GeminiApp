package com.example.firstgeminiapp.data.repository

import com.example.firstgeminiapp.data.remote.GeminiDataSource
import com.example.firstgeminiapp.domain.model.Summary
import com.example.firstgeminiapp.domain.repository.SummaryRepository
import com.example.firstgeminiapp.domain.Result
import com.example.firstgeminiapp.domain.model.Message

class SummaryRepositoryImpl(private val geminiDataSource: GeminiDataSource): SummaryRepository {

    override suspend fun getSummary(inputText: String): Result<Summary> {
       return geminiDataSource.generateSummary(inputText)
    }

    override suspend fun getAnswer(inputText: String): Result<Message> {
        return geminiDataSource.generateAnswer(inputText)
    }
}
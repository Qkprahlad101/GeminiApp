package com.example.firstgeminiapp.domain.repository

import com.example.firstgeminiapp.domain.model.Summary
import com.example.common.Utils.Result
import com.example.firstgeminiapp.domain.model.Message

interface SummaryRepository {
    suspend fun getSummary(inputText: String) : Result<Summary>

    suspend fun getAnswer(inputText: String) : Result<Message>
}
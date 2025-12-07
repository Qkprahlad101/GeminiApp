package com.example.firstgeminiapp.domain.usecase

import com.example.common.Utils.Result
import com.example.firstgeminiapp.domain.model.Message
import com.example.firstgeminiapp.domain.repository.SummaryRepository

class ConversationUseCase(private val summaryRepository: SummaryRepository) {
    suspend operator fun invoke(inputText: String): Result<Message> {
        return summaryRepository.getAnswer(inputText)
    }
}
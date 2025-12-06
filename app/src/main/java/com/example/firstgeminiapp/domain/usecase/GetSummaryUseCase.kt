package com.example.firstgeminiapp.domain.usecase

import com.example.firstgeminiapp.domain.model.Summary
import com.example.firstgeminiapp.domain.repository.SummaryRepository
import com.example.firstgeminiapp.domain.Result

class GetSummaryUseCase(private val summaryRepository: SummaryRepository) {
     suspend operator fun invoke(inputText: String): Result<Summary> {
        return summaryRepository.getSummary(inputText)
    }
}
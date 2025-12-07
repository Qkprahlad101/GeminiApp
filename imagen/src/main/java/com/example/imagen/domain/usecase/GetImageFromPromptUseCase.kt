package com.example.imagen.domain.usecase

import com.example.imagen.domain.repository.GetImageFromGeminiRepository

class GetImageFromPromptUseCase(private val repository: GetImageFromGeminiRepository) {

    suspend operator fun invoke(prompt: String) = repository.getImageFromPrompt(prompt)
}
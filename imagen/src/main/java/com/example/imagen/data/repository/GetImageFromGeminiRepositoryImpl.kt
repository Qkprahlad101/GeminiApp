package com.example.imagen.data.repository

import com.example.imagen.data.remote.GeminiImageSource
import com.example.imagen.domain.repository.GetImageFromGeminiRepository

class GetImageFromGeminiRepositoryImpl(private val geminiImageSource: GeminiImageSource) :
    GetImageFromGeminiRepository {
    override suspend fun getImageFromPrompt(prompt: String) =
        geminiImageSource.generateImage(prompt)
}
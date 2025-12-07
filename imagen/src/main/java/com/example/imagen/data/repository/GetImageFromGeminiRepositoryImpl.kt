package com.example.imagen.data.repository

import com.example.imagen.data.remote.ImageSource
import com.example.imagen.domain.repository.GetImageFromGeminiRepository

class GetImageFromGeminiRepositoryImpl(private val imageSource: ImageSource) :
    GetImageFromGeminiRepository {
    override suspend fun getImageFromPrompt(prompt: String) =
        imageSource.generateImage(prompt)
}
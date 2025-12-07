package com.example.imagen.domain.repository

import com.example.common.Utils.Result
import com.example.imagen.data.model.ImageResponse

interface GetImageFromGeminiRepository {

    suspend fun getImageFromPrompt(prompt: String) : Result<ImageResponse>
}
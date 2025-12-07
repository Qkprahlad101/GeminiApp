package com.example.imagen.data.remote

import android.graphics.BitmapFactory
import android.util.Log
import com.example.common.Utils.Result
import com.example.common.Utils.Util.DEBUG_LOG_TAG
import com.example.imagen.data.model.ImageResponse
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.ai.ImagenModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

class GeminiImageSource {
    private val model: ImagenModel by lazy {
        Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.5-flash") as ImagenModel
    }
    
    suspend fun generateImage(prompt: String) : Result<ImageResponse> {
        try {
            val response = model.generateImages(prompt)
            val image = response.images.firstOrNull() ?: throw Exception("No image generated")
            val bytes = image.data
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            return Result.Success<ImageResponse>(ImageResponse(bitmap))
        }catch (e: FirebaseException) {
            Log.d(DEBUG_LOG_TAG, "AI Service Error: ${e.message}")
            Result.Failure(ImageResponse(error ="AI Service Error: ${e.message}"))
        } catch (e: java.lang.Exception) {
            Log.d(DEBUG_LOG_TAG, "Failed to generate summary ${e.message}")
            Result.Failure(ImageResponse(error = "Failed to generate image: ${e.message}"))
        }
        return Result.Failure(ImageResponse(error = "Failed to generate image"))
    }
}

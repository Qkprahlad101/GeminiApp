package com.example.firstgeminiapp.data.remote

import android.util.Log
import com.example.firstgeminiapp.di.Utils.DEBUG_LOG_TAG
import com.example.firstgeminiapp.domain.model.Summary
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

class GeminiDataSource {
    private val model by lazy {
        try {
            Firebase.ai(backend = GenerativeBackend.googleAI())
                .generativeModel("gemini-2.5-flash")
        } catch (e: Exception) {
            throw FirebaseException("Failed to initialize Gemini model: ${e.message}", e)
        }
    }

    suspend fun generateSummary(inputText: String): Result<Summary> {
        if (inputText.isBlank()) return Result.failure(IllegalArgumentException("Input text cannot be empty"))

        val prompt = "Summarize the following text in a concise, bullet-point format (max 10 bullets, keep under 150 words): $inputText"
        return try {
            val response = model.generateContent(prompt)

            val data = response.text?.trim()?.ifBlank { "No meaningful summary generated—try a different text!" }
                ?: throw FirebaseException("Empty response from Gemini")
            Result.success(Summary(text = data, originalTextLength = inputText.length))
        } catch (e: FirebaseException) {
            Log.d(DEBUG_LOG_TAG, "AI Service Error: ${e.message}")
            Result.failure( FirebaseException("AI Service Error: ${e.message}", e))
        } catch (e: Exception) {
            Log.d(DEBUG_LOG_TAG, "Failed to generate summary ${e.message}")
            Result.failure(RuntimeException("Failed to generate summary: ${e.message}", e))
        }
    }
}
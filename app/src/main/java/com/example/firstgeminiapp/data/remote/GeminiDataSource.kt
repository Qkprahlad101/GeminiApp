package com.example.firstgeminiapp.data.remote

import android.util.Log
import com.example.firstgeminiapp.domain.model.Summary
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.example.common.Utils.Result
import com.example.common.Utils.Util.DEBUG_LOG_TAG
import com.example.firstgeminiapp.domain.model.Message
import com.google.firebase.ai.GenerativeModel

class GeminiDataSource {
    private val model: GenerativeModel by lazy {
        Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.5-flash")
    }


    suspend fun generateSummary(inputText: String): Result<Summary> {
        if (inputText.isBlank()) return Result.Failure(Summary(error = "Input text cannot be empty", ))

        val prompt = "Summarize the following text in a concise, bullet-point format (max 10 bullets, keep under 200 words): $inputText"
        return try {
            val response = model.generateContent(prompt)

            val data = response.text?.trim()?.ifBlank { "No meaningful summary generated—try a different text!" }
                ?: throw FirebaseException("Empty response from Gemini")
            Result.Success(Summary(text = data, originalTextLength = inputText.length, error = null))
        } catch (e: FirebaseException) {
            Log.d(DEBUG_LOG_TAG, "AI Service Error: ${e.message}")
            Result.Failure( Summary(error ="AI Service Error: ${e.message}"))
        } catch (e: Exception) {
            Log.d(DEBUG_LOG_TAG, "Failed to generate summary ${e.message}")
            Result.Failure(Summary(error = "Failed to generate summary: ${e.message}"))
        }
    }

    suspend fun generateAnswer(inputText: String) : Result<Message> {
        if (inputText.isBlank()) return Result.Failure(Message(error = "Input text cannot be empty"))
        return try {
            val response = model.generateContent(inputText)

            val data = response.text?.trim()?.ifBlank { "No meaningful summary generated—try a different text!" }
                ?: throw FirebaseException("Empty response from Gemini")
            Result.Success(Message(message = data, isUser = false))
        } catch (e: FirebaseException) {
            Log.d(DEBUG_LOG_TAG, "AI Service Error: ${e.message}")
            Result.Failure(  Message(error ="AI Service Error: ${e.message}", isUser = false))
        } catch (e: Exception) {
            Log.d(DEBUG_LOG_TAG, "Failed to generate summary ${e.message}")
            Result.Failure(Message(error = "Failed to generate summary: ${e.message}", isUser = false))
        }
    }

}
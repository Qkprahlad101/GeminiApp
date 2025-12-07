package com.example.imagen.data.remote

import com.google.ai.client.generativeai.GenerativeModel

class GeminiImageSource {
    private val model: GenerativeModel by lazy {
        Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.5-flash")
    }
}
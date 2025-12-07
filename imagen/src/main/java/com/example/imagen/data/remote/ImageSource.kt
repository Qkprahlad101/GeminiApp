package com.example.imagen.data.remote

import android.graphics.BitmapFactory
import android.util.Log
import com.example.common.Utils.Result
import com.example.common.Utils.Util.DEBUG_LOG_TAG
import com.example.imagen.BuildConfig
import com.example.imagen.data.model.ImageResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class ImageSource {

    private val client = OkHttpClient()

    suspend fun generateImage(prompt: String): Result<ImageResponse> {
        return try {
            val url = "https://fal.run/fal-ai/nano-banana"

            val jsonBody = JSONObject().apply {
                put("prompt", prompt)
                put("num_images", 1)
                put("aspect_ratio", "1:1")
                put("output_format", "png")
            }.toString()

            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Key ${BuildConfig.FAL_KEY}")
                .addHeader("Content-Type", "application/json")
                .post(jsonBody.toRequestBody("application/json".toMediaType()))
                .build()

            val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }

            if (!response.isSuccessful) {
                val err = response.body?.string()
                Log.d(DEBUG_LOG_TAG, "err: $err")
                return Result.Failure(ImageResponse(error = err ?: "Unknown error"))
            }

            val json = response.body?.string() ?: return Result.Failure(
                ImageResponse(error = "Empty response")
            )

            val imageUrl = JSONObject(json)
                .getJSONArray("images")
                .getJSONObject(0)
                .getString("url")

            val imageReq = Request.Builder().url(imageUrl).build()
            val imageRes = withContext(Dispatchers.IO) { client.newCall(imageReq).execute() }

            val bytes = imageRes.body?.bytes()
                ?: return Result.Failure(ImageResponse(error = "Empty image"))

            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            Log.d(DEBUG_LOG_TAG, "bitmap: $bitmap")

            Result.Success(ImageResponse(bitmap))

        } catch (e: Exception) {
            Log.d(DEBUG_LOG_TAG, "Exception: ${e.message}")
            Result.Failure(ImageResponse(error = e.message ?: "Unknown error"))
        }
    }
}




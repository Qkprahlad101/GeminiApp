package com.example.imagen.data.model

import android.graphics.Bitmap

data class ImageResponse(
    val data: Bitmap? = null,
    val error: String ? = null
)
package com.example.firstgeminiapp.domain.model

import java.util.UUID

data class Message(
    val message: String? = null,
    val isUser: Boolean = true,
    val messageId: String = UUID.randomUUID().toString(),
    val timeStamp: Long = System.currentTimeMillis(),
    val error: String? = null
)
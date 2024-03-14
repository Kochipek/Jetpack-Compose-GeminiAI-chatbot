package com.kochipek.geminichat.data

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ResponseStoppedException
import com.google.ai.client.generativeai.type.content
import com.kochipek.geminichat.data.model.ChatModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatData {
    val api_key = "YOUR_API_KEY"

    suspend fun getResponse(prompt: String) : ChatModel {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro", apiKey = api_key
        )
        return try {
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt)
            }
            ChatModel(
                prompt = response.text?:"error",
                bitmap = null,
                isFromUser = false
            )
        } catch (e: ResponseStoppedException) {
            ChatModel("Error", null, false)
        }
    }

    suspend fun getResponseWithImage(prompt: String, bitmap: Bitmap) : ChatModel {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro-vision", apiKey = api_key
        )
        try {
            val inputContent = content {
                image(bitmap)
                text(prompt)
            }
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(inputContent)
            }
            return ChatModel(
                prompt = response.text?:"error",
                bitmap = null,
                isFromUser = false
            )
        } catch (e: ResponseStoppedException) {
            return ChatModel("Error", null, false)
        }
    }
}
package com.kochipek.geminichat

import android.graphics.Bitmap
import com.kochipek.geminichat.data.model.ChatModel

data class ChatState(
    val chatList : MutableList<ChatModel> = mutableListOf(),
    val prompt : String = "",
    val bitmap : Bitmap? = null
)

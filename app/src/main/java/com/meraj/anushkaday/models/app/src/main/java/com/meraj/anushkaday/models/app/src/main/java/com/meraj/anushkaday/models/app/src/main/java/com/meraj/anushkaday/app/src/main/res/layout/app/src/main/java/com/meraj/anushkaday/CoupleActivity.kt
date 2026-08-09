package com.meraj.anushkaday

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.meraj.anushkaday.models.ChatMessage

class CoupleActivity : AppCompatActivity() {

    private lateinit var chatContainer: LinearLayout
    private lateinit var chatScroll: ScrollView
    private lateinit var etMessage: EditText
    private val messages = mutableListOf<ChatMessage>()
    private val prefsName = "AnushkaPrefs"
    private val chatKey = "chat_messages"
    private val partnerName = "Meraj"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_couple)

        chatContainer = findViewById(R.id.chatContainer)
        chatScroll = findViewById(R.id.chatScroll)
        etMessage = findViewById(R.id.etMessage)

        val btnSend = findViewById<Button>(R.id.btnSend)
        val btnHug = findViewById<TextView>(R.id.btnHug)
        val btnKiss = findViewById<TextView>(R.id.btnKiss)
        val btnWave = findViewById<TextView>(R.id.btnWave)
        val btnThinkingOfYou = findViewById<Button>(R.id.btnThinkingOfYou)

        loadMessages()
        renderMessages()

        btnSend.setOnClickListener {
            val text = etMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                addMessage(text)
                etMessage.text.clear()
            }
        }

        btnHug.setOnClickListener {
            addMessage("🤗 sent a hug")
            Toast.makeText(this, "Hug sent to $partnerName!", Toast.LENGTH_SHORT).show()
        }

        btnKiss.setOnClickListener {
            addMessage("😘 sent a kiss")
            Toast.makeText(this, "Kiss sent to $partnerName!", Toast.LENGTH_SHORT).show()
        }

        btnWave.setOnClickListener {
            addMessage("👋 waved")
            Toast.makeText(this, "Wave sent to $partnerName!", Toast.LENGTH_SHORT).show()
        }

        btnThinkingOfYou.setOnClickListener {
            addMessage("💌 Thinking of you ❤️")
            Toast.makeText(this, "$partnerName will feel your love!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addMessage(text: String) {
        messages.add(ChatMessage(text = text, isFromMe = true))
        saveMessages()
        renderMessages()
    }

    private fun renderMessages() {
        chatContainer.removeAllViews()

        for (message in messages) {
            val bubble = TextView(this).apply {
                text = message.text
                textSize = 15f
                setPadding(24, 16, 24, 16)
                setBackgroundColor(if (message.isFromMe) 0xFFFFE3EC.toInt() else 0xFFE9ECEF.toInt())
            }

            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = if (message.isFromMe) Gravity.END else Gravity.START
                setPadding(0, 4, 0, 4)
                addView(bubble)
            }

            chatContainer.addView(row)
        }

        chatScroll.post { chatScroll.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    private fun saveMessages() {
        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val serialized = messages.joinToString("\n") { msg ->
            "${if (msg.isFromMe) 1 else 0}\u0001${msg.timestamp}\u0001${msg.text}"
        }
        prefs.edit().putString(chatKey, serialized).apply()
    }

    private fun loadMessages() {
        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val serialized = prefs.getString(chatKey, "") ?: ""
        messages.clear()
        if (serialized.isNotEmpty()) {
            serialized.split("\n").forEach { line ->
                val parts = line.split("\u0001")
                if (parts.size == 3) {
                    messages.add(
                        ChatMessage(
                            text = parts[2],
                            isFromMe = parts[0] == "1",
                            timestamp = parts[1].toLongOrNull() ?: System.currentTimeMillis()
                        )
                    )
                }
            }
        }
    }
}

package com.meraj.anushkaday

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PlaceholderActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_EMOJI = "extra_emoji"
        const val EXTRA_TITLE = "extra_title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placeholder)

        val emoji = intent.getStringExtra(EXTRA_EMOJI) ?: "🚧"
        val title = intent.getStringExtra(EXTRA_TITLE) ?: "Feature"

        findViewById<TextView>(R.id.tvPlaceholderEmoji).text = emoji
        findViewById<TextView>(R.id.tvPlaceholderTitle).text = title
    }
}

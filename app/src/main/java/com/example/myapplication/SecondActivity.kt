package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textViewResult = findViewById<TextView>(R.id.textViewResult)

        // Получаем текст, который передали из MainActivity
        val receivedText = intent.getStringExtra("EXTRA_TEXT")

        if (receivedText != null) {
            textViewResult.text = receivedText
        } else {
            textViewResult.text = "Текст не был передан"
        }
    }
}
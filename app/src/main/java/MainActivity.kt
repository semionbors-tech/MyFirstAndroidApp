package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Объявляем переменные для наших элементов
    private lateinit var editTextInput: EditText
    private lateinit var btnOpenSecondActivity: Button
    private lateinit var btnCallFriend: Button
    private lateinit var btnShareText: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Убедитесь, что макет называется именно так

        // 1. Находим элементы по их ID из XML
        editTextInput = findViewById(R.id.editTextInput)
        btnOpenSecondActivity = findViewById(R.id.btnOpenSecondActivity)
        btnCallFriend = findViewById(R.id.btnCallFriend)
        btnShareText = findViewById(R.id.btnShareText)

        // 2. Явный Intent (Переход на вторую Activity)
        btnOpenSecondActivity.setOnClickListener {
            val text = editTextInput.text.toString().trim()

            // Валидация: проверяем, не пустой ли текст
            if (text.isEmpty()) {
                showToast("Пожалуйста, введите текст")
                return@setOnClickListener
            }

            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("EXTRA_TEXT", text)
            }
            startActivity(intent)
        }

        // 3. Неявный Intent (Звонок)
        btnCallFriend.setOnClickListener {
            val phoneNumber = editTextInput.text.toString().trim()

            // Валидация: проверяем на пустоту и на наличие букв
            if (phoneNumber.isEmpty()) {
                showToast("Введите номер телефона")
                return@setOnClickListener
            }
            if (!phoneNumber.matches(Regex("^[0-9+]*$"))) {
                showToast("Номер должен содержать только цифры и знак +")
                return@setOnClickListener
            }

            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }

            // Проверяем, есть ли вообще на устройстве звонилка
            if (dialIntent.resolveActivity(packageManager) != null) {
                startActivity(dialIntent)
            } else {
                showToast("На устройстве нет приложения для звонков")
            }
        }

        // 4. Системный Intent (Поделиться)
        btnShareText.setOnClickListener {
            val textToShare = editTextInput.text.toString().trim()

            if (textToShare.isEmpty()) {
                showToast("Нечего делиться, введите текст")
                return@setOnClickListener
            }

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, textToShare)
            }
            // Открываем системное окно выбора приложения
            startActivity(Intent.createChooser(shareIntent, "Поделиться через..."))
        }
    }

    // Вспомогательный метод для показа всплывающих сообщений (валидация)
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
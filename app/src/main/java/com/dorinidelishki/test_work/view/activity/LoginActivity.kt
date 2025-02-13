package com.dorinidelishki.test_work.view.activity

import android.content.Intent
import android.os.Bundle
import com.dorinidelishki.test_work.databinding.ActivityLoginBinding
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import com.dorinidelishki.test_work.R
import android.widget.Toast
import com.dorinidelishki.test_work.model.LoginModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val client = OkHttpClient.Builder().build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.regTV.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        //глазок пароля
        var isPasswordVisible = false
        binding.eyeIV.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.loginPasswordET.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.eyeIV.setImageResource(R.drawable.ic_eye_open)
            } else {
                binding.loginPasswordET.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.eyeIV.setImageResource(R.drawable.ic_eye_closed)
             }
             binding.loginPasswordET.setSelection(binding.loginPasswordET.text.length)
        }

        // Простая проверка заполненности полей
        binding.loginButton.setOnClickListener {
            var loginEmail = binding.loginEmailET.text.toString()
            var loginPassword = binding.loginPasswordET.text.toString()

            if (loginEmail.isEmpty() || loginPassword.isEmpty()) {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Создаем модельку логина
            val loginModel = LoginModel(email = loginEmail, password = loginPassword)

            //Выполняем запрос в фоне
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = loginUser(loginModel)
                    val responseBody = response.body?.string() //Читаем тело ответа

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful && responseBody != null) {
                            val jsonObject = JSONObject(responseBody)
                            //Проверка наличия токенаа
                            if (jsonObject.has("token")) {
                                val intent = Intent(this@LoginActivity, CheckInActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Ошибка входа: ${jsonObject.optString("error", "Неизвестная ошибка")}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Ошибка входа: ${response.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Ошибка: ${e.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }

    // Функция для выполнения запроса на логин
    private fun loginUser(loginModel: LoginModel) = client.newCall(
        Request.Builder()
            .url("https://app.successhotel.ru/api/client/login")
            .post(createRequestBody(loginModel))
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .build()
    ).execute()

    //Создание тела запроса из модели логина с Gson
    private fun createRequestBody(loginModel: LoginModel): okhttp3.RequestBody {
        val gson = Gson()
        val json = gson.toJson(loginModel)
        val mediaType = "application/json".toMediaType()
        return json.toRequestBody(mediaType)
    }
}
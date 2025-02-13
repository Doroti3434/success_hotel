package com.dorinidelishki.test_work.view.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dorinidelishki.test_work.R
import com.dorinidelishki.test_work.databinding.ActivityRegistrationBinding
import com.dorinidelishki.test_work.model.RegistrationModel
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

class RegistrationActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private val client = OkHttpClient.Builder().build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Видимость пароля
        var isPasswordVisible = false
        binding.eyeIV.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.regPasswordET.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.eyeIV.setImageResource(R.drawable.ic_eye_open)
            } else {
                binding.regPasswordET.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.eyeIV.setImageResource(R.drawable.ic_eye_closed)
            }
            binding.regPasswordET.setSelection(binding.regPasswordET.text.length)
        }


        binding.registrationButton.setOnClickListener {
            val firstName = binding.firstNameET.text.toString()
            val lastName = binding.lastNameET.text.toString()
            val email = binding.regEmailET.text.toString()
            val password = binding.regPasswordET.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Модель регистрации
            val registrationModel = RegistrationModel(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password,
                confirmPassword = password
            )

            // Выполняем сетевой запрос в фоне
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = registerUser(registrationModel)
                    val responseBody = response.body?.string()

                    Log.d("API_RESPONSE", "Response body: $responseBody")

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful && responseBody != null) {
                            val jsonObject = JSONObject(responseBody)

                            val isSuccess = jsonObject.optBoolean("success", false)
                            val message = jsonObject.optString("message", "Неизвестная ошибка")

                            Log.d("API_RESPONSE", "Success: $isSuccess, Message: $message")

                            Toast.makeText(this@RegistrationActivity, message, Toast.LENGTH_LONG).show()

                            if (isSuccess) {
                                val intent = Intent(this@RegistrationActivity, CheckInActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            Log.e("API_RESPONSE", "Ошибка: ${response.message}")
                            Toast.makeText(this@RegistrationActivity, "Ошибка регистрации: ${response.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.e("API_ERROR", "Ошибка: ${e.localizedMessage}")
                        Toast.makeText(this@RegistrationActivity, "Ошибка: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }
    }

    private fun registerUser(registrationModel: RegistrationModel) = client.newCall(
        Request.Builder()
            .url("https://app.successhotel.ru/api/client/register")
            .post(createRequestBody(registrationModel))
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .build()
    ).execute()
    private fun createRequestBody(registrationModel: RegistrationModel): okhttp3.RequestBody {
        val gson = Gson()
        val json = gson.toJson(registrationModel)
        val mediaType = "application/json".toMediaType()
        return json.toRequestBody(mediaType)
    }

}
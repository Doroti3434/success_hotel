package com.dorinidelishki.test_work.view.activity

import android.content.Intent
import android.os.Bundle
import com.dorinidelishki.test_work.databinding.ActivityLoginBinding
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import com.dorinidelishki.test_work.R


class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.regTV.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

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

    }

}
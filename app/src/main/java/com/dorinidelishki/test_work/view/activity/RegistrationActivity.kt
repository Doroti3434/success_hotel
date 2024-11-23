package com.dorinidelishki.test_work.view.activity

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import com.dorinidelishki.test_work.R
import com.dorinidelishki.test_work.databinding.ActivityRegistrationBinding

class RegistrationActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        }

    }
}
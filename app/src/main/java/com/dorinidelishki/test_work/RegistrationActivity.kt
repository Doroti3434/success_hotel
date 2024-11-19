package com.dorinidelishki.test_work

import android.app.Activity
import android.os.Bundle
import com.dorinidelishki.test_work.databinding.ActivityRegistrationBinding

class RegistrationActivity: Activity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
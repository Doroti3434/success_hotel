package com.dorinidelishki.test_work.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.dorinidelishki.test_work.R
import com.dorinidelishki.test_work.databinding.FragmentProfileBinding

class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

    }

}
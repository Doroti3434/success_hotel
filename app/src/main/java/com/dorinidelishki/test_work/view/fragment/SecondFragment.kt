package com.dorinidelishki.test_work.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dorinidelishki.test_work.R
import com.dorinidelishki.test_work.databinding.FragmentSecondBinding

class SecondFragment: Fragment(R.layout.fragment_second) {

    private lateinit var binding: FragmentSecondBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)

        binding.goodButton.setOnClickListener {
            findNavController().navigate(SecondFragmentDirections.actionSecondToThird())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}
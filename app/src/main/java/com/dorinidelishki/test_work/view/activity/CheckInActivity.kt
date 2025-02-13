package com.dorinidelishki.test_work.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Carousel.Adapter
import com.dorinidelishki.test_work.databinding.ActivityCheckInBinding

class CheckInActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCheckInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hotels = listOf("Отель 1")
        val rooms = listOf("13")

        val adapterHotel = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, hotels)
        binding.hotelDropDown.setAdapter(adapterHotel)
        val adapterRoom = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, rooms)
        binding.roomDropDown.setAdapter(adapterRoom)

        binding.hotelDropDown.setOnItemClickListener{ parent, view, position, id ->
            val selectedHotel = hotels[position]
        }
        binding.roomDropDown.setOnItemClickListener { parent, view, position, id ->
            val selectedRoom = rooms[position]
        }


    }

}
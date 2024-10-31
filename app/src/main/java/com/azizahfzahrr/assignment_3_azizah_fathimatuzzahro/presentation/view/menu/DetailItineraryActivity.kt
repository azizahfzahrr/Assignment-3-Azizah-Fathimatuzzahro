package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.R
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivityDetailItineraryBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailItineraryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailItineraryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailItineraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("itinerary_name")
        val location = intent.getStringExtra("itinerary_location")
        val popularity = intent.getFloatExtra("itinerary_popularity", 0f)
        val type = intent.getStringExtra("itinerary_type")
        val imageUrl = intent.getStringExtra("itinerary_image")

        binding.tvNameDestinationDetailItinerary.text = name
        binding.tvPlaceDestinationDetailItinerary.text = location
        binding.ratingBar.rating = popularity
        binding.tvRateDestination.text = popularity.toString()
        binding.tvFillTypeItinerary.text = type
        binding.cardBackButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        Glide.with(this)
            .load(imageUrl)
            .into(binding.ivDetailItinerary)
    }
}
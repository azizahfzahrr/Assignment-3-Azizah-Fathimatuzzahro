package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.itinerary

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.DetailTravelResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room.ItineraryEntity
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivityAddItineraryBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu.MainActivity
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu.ItineraryFragment
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.AddItineraryViewModel
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.utils.orEmpty
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddItineraryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItineraryBinding
    private val viewModel: AddItineraryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddItineraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val destinationData = intent.getSerializableExtra("destination_data") as? DetailTravelResponse.Data
        destinationData?.let { bindData(it) }
    }

    private fun bindData(data: DetailTravelResponse.Data) {
        binding.tvNameDestinationItinerary.text = data.name
        binding.tvPlaceDestinationItinerary.text = data.location
        binding.tvFillRatingItinerary.text = data.popularity.toString()
        binding.tvEstimate.text = data.duration

        Glide.with(this)
            .load(data.image)
            .into(binding.ivDestinationItinerary)

        binding.btnSaveItinerary.setOnClickListener {
            viewModel.saveItinerary(
                ItineraryEntity(
                    name = data.name.orEmpty(),
                    location = data.location.orEmpty(),
                    image = data.image.orEmpty(),
                    duration = data.duration.orEmpty(),
                    popularity = data.popularity.orEmpty(),
                    activity = data.activity.orEmpty(),
                    type = data.type.orEmpty(),
                    notes = binding.etNotes.text.toString()
                )
            )
            Intent(this, MainActivity::class.java).run{
                putExtra("action_type", "add")
                startActivity(this)
            }
        }
        binding.cardBackButton.setOnClickListener {
            onBackPressed()
        }
    }
}
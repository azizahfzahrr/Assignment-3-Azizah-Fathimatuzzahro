package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.DetailTravelResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivityDetailDestinationBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.itinerary.AddItineraryActivity
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu.ItineraryFragment
import com.bumptech.glide.Glide
import com.example.app.presentation.viewmodel.DestinationDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailDestinationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDestinationBinding
    private val viewModel: DestinationDetailViewModel by viewModels()
    private var destinationData: DetailTravelResponse.Data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val destinationId = intent.getIntExtra("destination_id", -1)
        if (destinationId != -1) {
            viewModel.getDestinationDetail(destinationId)
        } else {
            showError("Invalid destination ID")
            finish()
        }

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.destinationDetail.observe(this) { response ->
            response?.let {
                if (it.success == true) {
                    destinationData = it.data
                    bindData(it.data)
                } else {
                    showError("Failed to load destination details")
                }
            } ?: run {
                showError("Failed to load destination details")
            }
        }
    }

    private fun bindData(data: DetailTravelResponse.Data?) {
        if (data != null) {
            binding.tvNameDestinationDetail.text = data.name
            binding.tvPlaceDestinationDetail.text = data.location
            binding.tvFillAboutDestinationDetail.text = data.description

            Glide.with(this)
                .load(data.image)
                .into(binding.ivImageDetailDestination)

            binding.ratingBar.rating = data.popularity?.toFloat() ?: 0f
            binding.tvRateDestination.text = data.popularity?.toString() ?: "0"
            binding.tvActivityDetailDestination.text = data.activity?.toString() ?: "0"
        } else {
            showError("No details available")
        }
    }

    private fun setupClickListeners() {
        binding.cardBackButton.setOnClickListener {
            onBackPressed()
        }
        binding.btnSaveDestination.setOnClickListener {
            destinationData?.let { data ->
                val intent = Intent(this, AddItineraryActivity::class.java)
                intent.putExtra("destination_data", data) // serializable data
                startActivity(intent)
            } ?: run {
                showError("No destination data available to save")
            }
        }
    }

    private fun showError(message: String) {
        Log.e("DetailDestinationActivity", message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
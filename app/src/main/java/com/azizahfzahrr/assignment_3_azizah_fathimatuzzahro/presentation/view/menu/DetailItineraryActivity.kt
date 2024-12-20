package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivityDetailItineraryBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.DialogEditItineraryBinding
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailItineraryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailItineraryBinding
    private var itineraryNotes: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailItineraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("itinerary_name")
        val location = intent.getStringExtra("itinerary_location")
        val popularity = intent.getStringExtra("itinerary_popularity")
        val type = intent.getStringExtra("itinerary_type")
        val imageUrl = intent.getStringExtra("itinerary_image")
        val activity = intent.getStringExtra("itinerary_activity")
        val duration = intent.getStringExtra("itinerary_duration")
        itineraryNotes = intent.getStringExtra("itinerary_notes").orEmpty()

        binding.tvNameDestinationDetailItinerary.text = name
        binding.tvPlaceDestinationDetailItinerary.text = location
        binding.tvRateDestination.text = popularity
        binding.tvActivityDetailItinerary.text = activity
        binding.tvTypeDetailItinerary.text = type
        binding.tvFillEstimateDetailItinerary.text = duration
        binding.tvNotesDetailItinerary.text = itineraryNotes
        binding.cardBackButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        Glide.with(this)
            .load(imageUrl)
            .into(binding.ivDetailItinerary)

        binding.ivEditItinerary.setOnClickListener {
            showEditNotesDialog(itineraryNotes)
        }
        binding.ivDeleteItinerary.setOnClickListener {
            showDeleteDialog()
        }
    }

    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Itinerary")
            .setMessage("Are you sure you want to delete this itinerary?")
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Delete") { dialog, _ ->
                val resultIntent = Intent().apply {
                    putExtra("action_type", "delete")
                    putExtra("itinerary_name", intent.getStringExtra("itinerary_name"))
                }
                setResult(Activity.RESULT_OK, resultIntent)
                dialog.dismiss()
                finish()
            }
            .show()
    }

    private fun showEditNotesDialog(currentNotes: String) {
        val editDialogItinerary = DialogEditItineraryBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setView(editDialogItinerary.root)
            .setCancelable(false)
            .create()

        editDialogItinerary.etNotes.setText(currentNotes)
        editDialogItinerary.btnSave.setOnClickListener {
            val newNotes = editDialogItinerary.etNotes.text.toString()
            val resultIntent = Intent().apply {
                putExtra("updated_notes", newNotes)
                putExtra("action_type", "edit")
                putExtra("itinerary_name", intent.getStringExtra("itinerary_name"))
            }
            setResult(Activity.RESULT_OK, resultIntent)
            dialog.dismiss()
            finish()
        }

        editDialogItinerary.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
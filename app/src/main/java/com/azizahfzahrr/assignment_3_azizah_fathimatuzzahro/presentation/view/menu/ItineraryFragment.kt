package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room.ItineraryAdapter
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.FragmentItineraryBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.ItineraryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItineraryFragment : Fragment() {
    private lateinit var viewModel: ItineraryViewModel
    private lateinit var itineraryAdapter: ItineraryAdapter
    private lateinit var binding: FragmentItineraryBinding

    companion object {
        const val REQUEST_CODE_DETAIL_ITINERARY = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItineraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ItineraryViewModel::class.java]
        itineraryAdapter = ItineraryAdapter { itinerary ->
            val intent = Intent(context, DetailItineraryActivity::class.java).apply {
                putExtra("itinerary_name", itinerary.name)
                putExtra("itinerary_activity", itinerary.activity)
                putExtra("itinerary_duration", itinerary.duration)
                putExtra("itinerary_location", itinerary.location)
                putExtra("itinerary_popularity", itinerary.popularity)
                putExtra("itinerary_type", itinerary.type)
                putExtra("itinerary_image", itinerary.image)
                putExtra("itinerary_notes", itinerary.notes)
            }
            startActivityForResult(intent, REQUEST_CODE_DETAIL_ITINERARY)
        }
        binding.rvItinerary.layoutManager = LinearLayoutManager(context)
        binding.rvItinerary.adapter = itineraryAdapter

        viewModel.allItineraries.observe(viewLifecycleOwner) { itineraries ->
            if (itineraries.isNullOrEmpty()) {
                binding.ivNoData.visibility = View.VISIBLE
                binding.rvItinerary.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
            } else {
                binding.ivNoData.visibility = View.GONE
                binding.rvItinerary.visibility = View.VISIBLE
                binding.tvNoData.visibility = View.GONE
                itineraryAdapter.submitList(itineraries)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("azizah", "onActivityResult $resultCode, ${data?.getStringExtra("updated_notes")}, ${data?.getStringExtra("itinerary_name")}, ${data?.getStringExtra("action_type")}")

        if (resultCode == Activity.RESULT_OK) {
            val itineraryName = data?.getStringExtra("itinerary_name")
            val actionType = data?.getStringExtra("action_type")

            when (actionType) {
                "edit" -> {
                    val updateNotes = data.getStringExtra("updated_notes") ?: return
                    val updatedList = viewModel.allItineraries.value?.map { itinerary ->
                        if (itinerary.name == itineraryName) {
                            itinerary.copy(notes = updateNotes)
                        } else {
                            itinerary
                        }
                    }
                    itineraryAdapter.submitList(updatedList)
                }
                "delete" -> {
                    itineraryName?.let { name ->
                        val itineraryToDelete = viewModel.allItineraries.value?.find { it.name == name }
                        itineraryToDelete?.let { viewModel.deleteItinerary(it) }
                    }
                }
            }
        }
    }
}
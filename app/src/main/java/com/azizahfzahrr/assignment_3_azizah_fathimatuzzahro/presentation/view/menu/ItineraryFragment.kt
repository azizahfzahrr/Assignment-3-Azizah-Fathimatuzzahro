package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.R
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room.ItineraryAdapter
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room.ItineraryEntity
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.FragmentItineraryBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.FragmentProfileBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.ItineraryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItineraryFragment : Fragment() {

    private lateinit var viewModel: ItineraryViewModel
    private lateinit var itineraryAdapter: ItineraryAdapter
    private lateinit var binding: FragmentItineraryBinding

    companion object{
        private const val REQUEST_CODE_DETAIL_ITINERARY = 2
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
            val intent = Intent(requireContext(), DetailItineraryActivity::class.java).apply {
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
        if (requestCode == REQUEST_CODE_DETAIL_ITINERARY && resultCode == Activity.RESULT_OK){
            val updateNotes = data?.getStringExtra("updated_notes")
            if (updateNotes != null){
                val updateList = viewModel.allItineraries.value?.map { itinerary ->
                    if (itinerary.name == data.getStringExtra("itinerary_name")){
                        itinerary.copy(notes = updateNotes)
                    } else {
                        itinerary
                    }
                }
                itineraryAdapter.submitList(updateList)
            }
        }
    }
}

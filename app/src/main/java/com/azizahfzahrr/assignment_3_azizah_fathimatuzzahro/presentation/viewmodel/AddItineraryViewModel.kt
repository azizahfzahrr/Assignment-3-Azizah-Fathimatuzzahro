package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room.ItineraryEntity
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.ItineraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItineraryViewModel @Inject constructor(private val repository: ItineraryRepository) : ViewModel() {

    fun saveItinerary(itinerary: ItineraryEntity) {
        viewModelScope.launch {
            repository.insert(itinerary)
        }
    }
}

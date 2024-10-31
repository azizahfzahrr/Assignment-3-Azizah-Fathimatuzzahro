package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.remote.ApiService
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room.ItineraryEntity
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.ItineraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ItineraryViewModel @Inject constructor(
    private val itineraryRepository: ItineraryRepository
) : ViewModel() {

//    private val _itineraryData = MutableLiveData<ItineraryEntity?>()
//    val itineraryData: LiveData<ItineraryEntity?> get() = _itineraryData

    val allItineraries: LiveData<List<ItineraryEntity>> = itineraryRepository.getAllItineraries()

//    fun insertItinerary(itinerary: ItineraryEntity) {
//        viewModelScope.launch {
//            try {
//                itineraryRepository.insert(itinerary)
//                _itineraryData.postValue(itinerary)
//            } catch (e: Exception) {
//                Log.e("ItineraryViewModel", "Error inserting itinerary: ${e.message}")
//            }
//        }
//    }
}





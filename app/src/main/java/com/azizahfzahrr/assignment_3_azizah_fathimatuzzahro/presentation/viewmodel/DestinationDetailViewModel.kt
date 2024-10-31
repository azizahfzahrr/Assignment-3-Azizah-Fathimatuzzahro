package com.example.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.DetailTravelResponse
import com.example.app.domain.usecase.DestinationDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinationDetailViewModel @Inject constructor(
    private val destinationDetailUseCase: DestinationDetailUseCase
) : ViewModel() {

    private val _destinationDetail = MutableLiveData<DetailTravelResponse?>()
    val destinationDetail: LiveData<DetailTravelResponse?> = _destinationDetail

    fun getDestinationDetail(destinationId: Int) {
        viewModelScope.launch {
            try {
                val response = destinationDetailUseCase(destinationId)
                _destinationDetail.value = response
            } catch (e: Exception) {
                Log.e("DestinationDetailViewModel", "Error fetching destination detail", e)
                _destinationDetail.value = null
            }
        }
    }
}

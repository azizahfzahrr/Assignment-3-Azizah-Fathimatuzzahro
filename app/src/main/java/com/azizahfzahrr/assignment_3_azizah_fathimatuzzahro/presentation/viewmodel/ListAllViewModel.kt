package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.ListResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.TravelResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.TravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListAllViewModel @Inject constructor(
    private val repository: TravelRepository
) : ViewModel() {

    private val _allDestinations = MutableLiveData<MutableList<TravelResponse.Data>>()
    val allDestinations: LiveData<MutableList<TravelResponse.Data>> get() = _allDestinations

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private var currentPage = 1
    private var isLastPageReached = false

    init {
        _allDestinations.value = mutableListOf() // Initialize the list
    }

    fun fetchAllDestinations() {
        if (_isLoading.value == true || isLastPageReached) return

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.fetchAllDestinations(currentPage)
                if (response?.success == true) {
                    val newData = response.data?.filterNotNull() ?: emptyList()
                    if (newData.isEmpty()) {
                        isLastPageReached = true
                    } else {
                        _allDestinations.value?.addAll(newData)
                        _allDestinations.postValue(_allDestinations.value) // Notify observers of change
                        currentPage++
                    }
                } else {
                    _errorMessage.value = response?.message ?: "Failed to fetch data."
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

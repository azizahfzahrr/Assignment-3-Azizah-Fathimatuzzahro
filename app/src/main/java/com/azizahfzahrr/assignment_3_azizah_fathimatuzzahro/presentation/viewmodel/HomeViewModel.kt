package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.TravelResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.TravelRepository
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TravelRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _travelData = MutableLiveData<List<TravelResponse.Data>>()
    val travelData: LiveData<List<TravelResponse.Data>> get() = _travelData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isLastPage = MutableLiveData<Boolean>(false)
    val isLastPage: LiveData<Boolean> get() = _isLastPage

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _userData = MutableLiveData<LoginResponse?>()
    val userData: LiveData<LoginResponse?> get() = _userData

    private var currentPage = 1
    private var isLastPageReached = false

    private var originalDestinations: List<TravelResponse.Data> = emptyList()

    fun fetchTravelData() {
        if (_isLoading.value == true || isLastPageReached) return

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.fetchTravelData(currentPage)
                if (response != null && response.success == true) {
                    val newData = response.data?.filterNotNull() ?: emptyList()
                    if (newData.isEmpty()) {
                        isLastPageReached = true
                        _isLastPage.value = true
                    } else {
                        _travelData.value = (_travelData.value ?: emptyList()) + newData
                        originalDestinations = _travelData.value ?: emptyList()
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

    fun fetchUserData() {
        viewModelScope.launch {
            try {
                val loginResponse = userRepository.getUser()
                _userData.value = loginResponse
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching user data: ${e.localizedMessage}"
            }
        }
    }
}
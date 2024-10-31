package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _selectedType = MutableLiveData<String?>()
    val selectedType: LiveData<String?> = _selectedType

    fun fetchTravelTypes(): LiveData<List<String?>?> {
        val result = MutableLiveData<List<String?>?>()
        viewModelScope.launch {
            val response = preferencesRepository.fetchTravelTypes()
            result.value = response?.data
        }
        return result
    }

    fun setSelectedType(type: String){
        _selectedType.value = type
    }
}

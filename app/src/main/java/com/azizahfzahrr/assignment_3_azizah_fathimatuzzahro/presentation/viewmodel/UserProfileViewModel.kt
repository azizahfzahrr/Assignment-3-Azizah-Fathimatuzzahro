package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.UserRepository
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.domain.usecase.FetchUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val fetchUserProfileUseCase: FetchUserProfileUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState: StateFlow<UserState> get() = _userState

    init {
        fetchUserProfile()
    }

    fun fetchUserProfile() {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                val response = fetchUserProfileUseCase()
                if (response?.success == true) {
                    _userState.value = UserState.Success(response.data)
                } else {
                    _userState.value = UserState.Error("Failed to load user profile")
                }
            } catch (e: Exception) {
                _userState.value = UserState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class UserState {
    object Loading : UserState()
    data class Success(val user: LoginResponse.Data?) : UserState()
    data class Error(val message: String?) : UserState()
    object Logout : UserState()
}

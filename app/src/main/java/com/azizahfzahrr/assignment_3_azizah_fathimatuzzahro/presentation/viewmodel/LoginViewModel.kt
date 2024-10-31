package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.local.UserSession
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val userSession: UserSession
) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    val isUserLoggedIn: Flow<Boolean> get() = userSession.isUserLoggedIn

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = loginUseCase.executeLogin(email, password)
                _loginResult.value = response
                if (response.success == true) {
                    response.data?.let { userData ->
                        userSession.saveUserData(userData)
                    }
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Login error: ${e.message}")
                _loginResult.value = LoginResponse(success = false, message = e.message, data = null)
            }
        }
    }
}
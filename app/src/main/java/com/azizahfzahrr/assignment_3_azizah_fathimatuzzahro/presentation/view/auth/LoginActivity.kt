package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivityLoginBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu.PreferencesActivity
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    companion object {
        private const val PREFS_NAME = "UserPrefs"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkIfUserLoggedIn()

        binding.btnLogin.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.login(email, password)
            } else {
                showError("Please enter both email and password")
            }
        }

        observeLoginResult()
    }

    private fun checkIfUserLoggedIn() {
        val isLoggedIn = sharedPreferences().getBoolean(KEY_IS_LOGGED_IN, false)

        if (isLoggedIn) {
            navigateToPreferencesActivity()
        } else {
            lifecycleScope.launch {
                loginViewModel.isUserLoggedIn.collect { loggedIn ->
                    if (loggedIn) {
                        saveLoginState()
                        navigateToPreferencesActivity()
                    }
                }
            }
        }
    }

    private fun saveLoginState() {
        sharedPreferences().edit().putBoolean(KEY_IS_LOGGED_IN, true).apply()
    }

    private fun sharedPreferences(): SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun navigateToPreferencesActivity() {
        val intent = Intent(this, PreferencesActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun observeLoginResult() {
        loginViewModel.loginResult.observe(this) { response ->
            if (response.success == true) {
                saveLoginState()
                navigateToPreferencesActivity()
            } else {
                val errorMessage = response.message ?: "Login failed. Please try again."
                showError(errorMessage)
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        Log.e("LoginActivity", message)
    }
}
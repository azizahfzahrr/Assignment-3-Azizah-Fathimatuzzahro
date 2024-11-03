package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivitySplashScreenBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.onboarding.OnboardingActivity
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val loginViewModel: LoginViewModel by viewModels()

    companion object {
        private const val PREFS_NAME = "UserPrefs"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkIfUserLoggedIn()
    }

    private fun sharedPreferences(): SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun checkIfUserLoggedIn() {
        val isLoggedIn = sharedPreferences().getBoolean(KEY_IS_LOGGED_IN, false)
        if (isLoggedIn) {
            navigateToMainActivity() // Navigate to MainActivity if logged in
        } else {
            // If not logged in, show onboarding after a delay
            lifecycleScope.launch {
                delay(3000) // Delay for 3 seconds
                if (!isFinishing) {
                    startActivity(Intent(this@SplashScreen, OnboardingActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
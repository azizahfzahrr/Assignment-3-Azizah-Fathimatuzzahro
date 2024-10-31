package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivitySplashScreenBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.onboarding.OnboardingActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(3000)
            startActivity(Intent(this@SplashScreen, OnboardingActivity::class.java))
            finish()
        }
    }
}
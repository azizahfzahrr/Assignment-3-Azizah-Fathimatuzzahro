package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivityOnboardingBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.auth.LoginActivity

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNextOnboard1.setOnClickListener {
            val intent = Intent(this, OnboardingActivity2::class.java)
            startActivity(intent)
        }

        binding.tvSkipOnboard1.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
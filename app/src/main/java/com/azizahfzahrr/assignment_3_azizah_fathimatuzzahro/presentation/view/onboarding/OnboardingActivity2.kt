package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivityOnboarding2Binding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.auth.LoginActivity

class OnboardingActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityOnboarding2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboarding2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNextOnboard2.setOnClickListener {
            val intent = Intent(this, OnboardingActivity3::class.java)
            startActivity(intent)
        }

        binding.tvSkipOnboard1.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
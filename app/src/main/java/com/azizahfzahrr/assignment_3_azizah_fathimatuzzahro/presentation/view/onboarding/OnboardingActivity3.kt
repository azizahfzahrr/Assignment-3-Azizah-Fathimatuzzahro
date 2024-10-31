package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivityOnboarding3Binding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.auth.LoginActivity

class OnboardingActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityOnboarding3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboarding3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNextOnboard3.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.tvSkipOnboard1.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
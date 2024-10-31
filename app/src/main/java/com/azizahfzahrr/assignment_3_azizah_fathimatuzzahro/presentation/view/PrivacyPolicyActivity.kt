package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.R
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivityPrivacyPolicyBinding

class PrivacyPolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrivacyPolicyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvConcelationPolicy.text = getString(R.string.cancellation_policy)
        binding.tvTermsConditions.text = getString(R.string.terms_and_conditions)

        binding.tvCancellationPoint1.text = getString(R.string.cancellation_point_1)
        binding.tvCancellationPoint2.text = getString(R.string.cancellation_point_2)

        binding.tvTermsPoint1.text = getString(R.string.terms_point_1)
        binding.tvTermsPoint2.text = getString(R.string.terms_point_2)
        binding.tvTermsPoint3.text = getString(R.string.terms_point_3)

        binding.cardBackButtonPrivacyPolicy.setOnClickListener {
            finish()
        }
    }
}
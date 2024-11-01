package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivityDetailProfileBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.UserProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProfileActivity : AppCompatActivity() {

    private val viewModel: UserProfileViewModel by viewModels()
    private lateinit var binding: ActivityDetailProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivArrowLeftEdit.setOnClickListener {
            onBackPressed()
        }

        val firstName = intent.getStringExtra("EXTRA_FIRST_NAME") ?: "Unknown"
        val lastName = intent.getStringExtra("EXTRA_LAST_NAME") ?: "Unknown"
        val email = intent.getStringExtra("EXTRA_EMAIL") ?: "Unknown"
        val phone = intent.getStringExtra("EXTRA_PHONE") ?: "Unknown"
        val avatarUrl = intent.getStringExtra("EXTRA_AVATAR")

        populateUserProfile(firstName, lastName, email, phone, avatarUrl)

        // Remove observeUserProfile if not necessary
        // Commenting out the function to avoid unnecessary state emissions
        // observeUserProfile()
    }

    private fun populateUserProfile(firstName: String?, lastName: String?, email: String?, phone: String?, avatarUrl: String?) {
        binding.etFirstName.setText(firstName)
        binding.etLastName.setText(lastName)
        binding.etEmail.setText(email)
        binding.etPhone.setText(phone)

        avatarUrl?.let {
            Glide.with(this)
                .load(it)
                .circleCrop()
                .into(binding.ivAvatarEditProfile)
        } ?: run {

            Glide.with(this)
                .load("https://cdn.antaranews.com/cache/1200x800/2023/03/01/6C28C3C3-550F-4868-9E6A-641681A377AA.jpeg.webp")
                .circleCrop()
                .into(binding.ivAvatarEditProfile)
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(this, message ?: "An error occurred", Toast.LENGTH_SHORT).show()
    }
}
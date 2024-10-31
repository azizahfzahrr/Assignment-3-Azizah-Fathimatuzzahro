package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivityDetailProfileBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.UserProfileViewModel
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.UserState
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailProfileActivity : AppCompatActivity() {

    private val viewModel: UserProfileViewModel by viewModels()
    private lateinit var binding: ActivityDetailProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeUserProfile()
    }

    private fun observeUserProfile() {
        lifecycleScope.launch {
            viewModel.userState.collect { state ->
                when (state) {
                    is UserState.Loading -> {
                        // buat nampil loading kalo perlu
                    }
                    is UserState.Success -> {
                        populateUserProfile(state.user)
                    }
                    is UserState.Error -> {
                        showError(state.message)
                    }

                    UserState.Logout -> TODO()
                }
            }
        }
    }

    private fun populateUserProfile(data: LoginResponse.Data?) {
        data?.let {
            Glide.with(this)
                .load(it.avatar)
                .circleCrop()
                .into(binding.ivAvatarEditProfile)

            binding.etFirstName.setText(it.firstName)
            binding.etLastName.setText(it.lastName)
            binding.etEmail.setText(it.email)
            binding.etPhone.setText(it.phone)
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(this, message ?: "An error occurred", Toast.LENGTH_SHORT).show()
    }
}
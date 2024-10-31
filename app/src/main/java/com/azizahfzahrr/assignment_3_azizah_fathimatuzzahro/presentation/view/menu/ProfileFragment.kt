package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.FragmentProfileBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.auth.LoginActivity
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.UserProfileViewModel
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.UserState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: UserProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.userState.collect { state ->
                when (state) {
                    is UserState.Success -> {
                        state.user?.let { user ->
                            saveUserToLocal(user)
                            displayUser(user)
                        } ?: loadUserFromLocal()
                    }
                    is UserState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                    is UserState.Loading -> {
                        // Show loading state if needed
                    }
                    UserState.Logout -> {
                        startActivity(Intent(requireContext(), LoginActivity::class.java))
                        activity?.finish()
                    }
                }
            }
        }

        binding.arrowEditPreferences.setOnClickListener {
            startActivity(Intent(requireContext(), PreferencesActivity::class.java))
        }

        binding.arrowPrivacyPolicy.setOnClickListener {
            startActivity(Intent(requireContext(), PrivacyPolicyActivity::class.java))
        }

        binding.ivArrowRightProfile.setOnClickListener {
            startActivity(Intent(requireContext(), DetailProfileActivity::class.java))
        }

        binding.tvLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun displayUser(user: LoginResponse.Data?) {
        Glide.with(this)
            .load(user?.avatar)
            .circleCrop()
            .into(binding.profileImage)

        binding.apply {
            tvFirstName.text = user?.firstName ?: "No first name"
            tvLastName.text = user?.lastName ?: "No last name"
            tvEmailProfile.text = user?.email ?: "No email"
        }
    }

    private fun saveUserToLocal(user: LoginResponse.Data) {
        val sharedPreferences = requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("avatar", user.avatar)
            putString("firstName", user.firstName)
            putString("lastName", user.lastName)
            putString("email", user.email)
            apply()
        }
    }

    private fun loadUserFromLocal() {
        val sharedPreferences = requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val avatar = sharedPreferences.getString("avatar", null)
        val firstName = sharedPreferences.getString("firstName", "No first name")
        val lastName = sharedPreferences.getString("lastName", "No last name")
        val email = sharedPreferences.getString("email", "No email")

        displayUser(LoginResponse.Data(avatar, firstName, lastName, email, null, null))
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            logoutUser()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    private fun logoutUser() {
        clearLocalUserData()
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finish()
    }

    private fun clearLocalUserData() {
        val sharedPreferences = requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}
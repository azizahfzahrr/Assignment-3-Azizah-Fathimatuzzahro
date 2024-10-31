package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivityPreferencesBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.PreferencesViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreferencesActivity : AppCompatActivity() {

    private lateinit var preferencesViewModel: PreferencesViewModel
    private var _binding: ActivityPreferencesBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("filters", Context.MODE_PRIVATE)

        preferencesViewModel = ViewModelProvider(this).get(PreferencesViewModel::class.java)
        fetchTravelTypes()

        binding.btnContinue.setOnClickListener {
            navigateToHomeFragment()
        }
    }

    private fun fetchTravelTypes() {
        preferencesViewModel.fetchTravelTypes().observe(this) { dataList ->
            if (!dataList.isNullOrEmpty()) {
                populateChipGroup(dataList)
            } else {
                Toast.makeText(this, "No travel types available.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun populateChipGroup(dataList: List<String?>) {
        binding.chipGroup.removeAllViews()
        for (type in dataList) {
            type?.let {
                val chip = Chip(this).apply {
                    text = it
                    isCheckable = true
                    isChecked = it == sharedPreferences.getString("selectedFilter", "")

                    setOnClickListener {
                        preferencesViewModel.setSelectedType(text.toString())
                        sharedPreferences.edit().putString("selectedFilter", text.toString()).apply()
                    }
                }
                binding.chipGroup.addView(chip)
            }
        }
    }

    private fun navigateToHomeFragment() {
        val selectedType = sharedPreferences.getString("selectedFilter", "")
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("selectedType", selectedType)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.R
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivityMainBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.utils.orEmpty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val selectedType by lazy { sharedPreferences.getString("selectedFilter", "") }
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private var defaultFragment: Fragment = HomeFragment()
        fun launchIntent(caller: Activity, fragment: Fragment) {
            defaultFragment = fragment
            caller.startActivity(Intent(caller, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            })
            caller.finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("filters", Context.MODE_PRIVATE)
        replaceFragment(HomeFragment.newInstance(selectedType))

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    ContextCompat.getColorStateList(this, R.color.bottom_navigation_item_color)
                    replaceFragment(HomeFragment.newInstance(selectedType))
                    true
                }

                R.id.nav_itinerary -> {
                    ContextCompat.getColorStateList(this, R.color.bottom_navigation_item_color)
                    replaceFragment(ItineraryFragment())
                    true
                }

                R.id.nav_profile -> {
                    ContextCompat.getColorStateList(this, R.color.bottom_navigation_item_color)
                    replaceFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun checkFragment(fragment: String, selectedType: String?) {
        if (defaultFragment is ItineraryFragment) return
        when (fragment) {
            "add" -> {
                defaultFragment = ItineraryFragment()
                binding.bottomNavigationView.menu.findItem(R.id.nav_itinerary).isChecked = true
                replaceFragment(defaultFragment)
            }

            else -> {
                binding.bottomNavigationView.menu.findItem(R.id.nav_home).isChecked = true
                replaceFragment(HomeFragment.newInstance(selectedType))
            }
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStartactivity: ")
        checkFragment(intent.extras?.getString("action_type").orEmpty(), selectedType)
    }
}
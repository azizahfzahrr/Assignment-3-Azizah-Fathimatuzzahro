package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.R
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivityMainBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu.HomeFragment
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu.ItineraryFragment
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val selectedType by lazy { intent.getStringExtra("selectedType") }

    companion object{
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
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment.newInstance(selectedType))

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    ContextCompat.getColorStateList(this, R.color.bottom_navigation_item_color)
                    replaceFragment(HomeFragment())
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

    private fun checkFragment(fragment: Fragment, selectedType: String?){
        when(fragment){
            is HomeFragment -> {
                binding.bottomNavigationView.menu.findItem(R.id.nav_home).isChecked = true
                replaceFragment(HomeFragment.newInstance(selectedType))
            }
            is ItineraryFragment -> {
                binding.bottomNavigationView.menu.findItem(R.id.nav_itinerary).isChecked = true
                replaceFragment(defaultFragment)
                defaultFragment = HomeFragment()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkFragment(defaultFragment, selectedType)
    }
}
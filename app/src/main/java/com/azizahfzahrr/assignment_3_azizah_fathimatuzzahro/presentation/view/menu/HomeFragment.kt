package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.R
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.TravelResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.FragmentHomeBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.adapter.DestinationAdapter
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.HomeViewModel
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.UserProfileViewModel
import com.bumptech.glide.Glide
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.detail.DetailDestinationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private val userProfileViewModel: UserProfileViewModel by viewModels()
    private lateinit var destinationAdapter: DestinationAdapter

    private var originalDestinations: List<TravelResponse.Data?> = emptyList()
    private var selectedType: String? = null

    companion object {
        fun newInstance(selectedType: String?): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString("selectedType", selectedType)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedType = arguments?.getString("selectedType")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedType = arguments?.getString("selectedType")

        binding.tvTitle.text = getString(R.string.explore_the_world_today)
        binding.tvSubtitle.text = getString(R.string.discover_take_your_travel_to_next_level)
        binding.tvViewAllList.setOnClickListener {
            val intent = Intent(activity, ListAllActivity::class.java)
            startActivity(intent)
        }

        setupRecyclerView()
        setupSwipeRefreshLayout()
        setupSearchFunctionality()

        lifecycleScope.launch {
            userProfileViewModel.fetchUserProfile()
            homeViewModel.fetchTravelData()
        }
    }

    private fun setupSearchFunctionality() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString()
                filterDestinations(searchText)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterDestinations(query: String) {
        val trimmedQuery = query.trim()
        val filteredList = if (trimmedQuery.isEmpty()) {
            originalDestinations.filter { it?.type == selectedType }
        } else {
            originalDestinations.filter { destination ->
                val nameMatches = destination?.name?.contains(trimmedQuery, ignoreCase = true) ?: false
                val typeMatches = destination?.type == selectedType
                nameMatches && typeMatches
            }
        }

        destinationAdapter.setDestinations(filteredList)
    }

    private fun setupRecyclerView() {
        destinationAdapter = DestinationAdapter { destinationId ->
            val intent = Intent(activity, DetailDestinationActivity::class.java).apply {
                putExtra("destination_id", destinationId)
            }
            startActivity(intent)
        }

        binding.rvDestination.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = destinationAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount = childCount
                    val totalItemCount = adapter?.itemCount ?: 0
                    val firstVisibleItemPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (!homeViewModel.isLoading.value!! &&
                        visibleItemCount + firstVisibleItemPosition >= totalItemCount &&
                        firstVisibleItemPosition >= 0) {
                        homeViewModel.fetchTravelData()
                    }
                }
            })
        }
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.fetchTravelData()
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }

        homeViewModel.travelData.observe(viewLifecycleOwner) { travelData ->
            if (travelData.isNullOrEmpty()) {
                showError("No travel data available.")
            } else {
                originalDestinations = travelData.filterNotNull()
                val filteredData = travelData.filter { it.type == selectedType }
                destinationAdapter.setDestinations(filteredData)
            }
        }

        homeViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                showError(errorMessage)
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
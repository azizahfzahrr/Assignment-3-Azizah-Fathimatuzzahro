package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.menu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ActivityListAllBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.adapter.DestinationAdapter
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.detail.DetailDestinationActivity
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.ListAllViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListAllActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListAllBinding
    private val listAllViewModel: ListAllViewModel by viewModels()
    private lateinit var destinationAdapter: DestinationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        listAllViewModel.fetchAllDestinations()

        binding.cardBackButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        destinationAdapter = DestinationAdapter { destinationId ->
            val intent = Intent(this, DetailDestinationActivity::class.java).apply {
                putExtra("destination_id", destinationId)
            }
            startActivity(intent)
        }
        binding.rvListAllDestination.apply {
            layoutManager = LinearLayoutManager(this@ListAllActivity)
            adapter = destinationAdapter

            // Adding pagination logic
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = this@apply.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (!listAllViewModel.isLoading.value!! &&
                        visibleItemCount + firstVisibleItemPosition >= totalItemCount &&
                        firstVisibleItemPosition >= 0) {
                        listAllViewModel.fetchAllDestinations() // Fetch more data
                    }
                }
            })
        }
    }

    private fun observeViewModel() {
        listAllViewModel.allDestinations.observe(this) { destinations ->
            if (destinations != null) {
                destinationAdapter.setDestinations(destinations)
            }
        }

        listAllViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                destinationAdapter.showLoading()
            } else {
                destinationAdapter.hideLoading()
                binding.shimmerContainer.stopShimmer()
                binding.shimmerContainer.hideShimmer()
            }
        }

        listAllViewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        }
    }
}
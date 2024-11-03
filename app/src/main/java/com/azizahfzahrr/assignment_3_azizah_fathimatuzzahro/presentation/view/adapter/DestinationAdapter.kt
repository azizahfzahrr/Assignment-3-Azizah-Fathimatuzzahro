package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.TravelResponse
import com.bumptech.glide.Glide
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ItemDestinationRowBinding
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ItemDestinationShimmerBinding

class DestinationAdapter(private val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val destinations = mutableListOf<TravelResponse.Data?>()
    private var isLoading = false

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_SHIMMER = 1
    }

    inner class DestinationViewHolder(private val binding: ItemDestinationRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(destination: TravelResponse.Data) {
            binding.tvNameDestination.text = destination.name
            binding.tvPlaceDestination.text = destination.location
            binding.tvTypeDestination.text = destination.type

            destination.image?.let {
                Glide.with(binding.root.context)
                    .load(it)
                    .into(binding.ivDestination)
            }

            val popularity = destination.popularity?.toFloatOrNull()
            binding.ratingBar.rating = popularity ?: 0f
            binding.tvRateDestination.text = popularity?.toString() ?: "N/A"

            binding.root.setOnClickListener {
                destination.id?.let { id -> onItemClick(id) }
            }
        }
    }

    inner class ShimmerViewHolder(binding: ItemDestinationShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val binding = ItemDestinationRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            DestinationViewHolder(binding)
        } else {
            val binding = ItemDestinationShimmerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            ShimmerViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DestinationViewHolder) {
            val destination = destinations.getOrNull(position)
            destination?.let { holder.bind(it) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position >= destinations.size) VIEW_TYPE_SHIMMER else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return if (isLoading) destinations.size + 1 else destinations.size
    }

    fun setDestinations(newDestinations: List<TravelResponse.Data?>) {
        destinations.clear() // Clear existing items for initial load
        destinations.addAll(newDestinations)
        notifyDataSetChanged()
    }

    fun updateDestinations(newDestinations: List<TravelResponse.Data?>) {
        val startPosition = destinations.size
        destinations.addAll(newDestinations)
        notifyItemRangeInserted(startPosition, newDestinations.size)
    }

    fun showLoading() {
        if (!isLoading) {
            isLoading = true
            notifyItemInserted(destinations.size)
        }
    }

    fun hideLoading() {
        if (isLoading) {
            isLoading = false
            notifyItemRemoved(destinations.size)
        }
    }
}
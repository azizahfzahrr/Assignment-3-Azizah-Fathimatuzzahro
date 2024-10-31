package com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.databinding.ItemDestinationRowBinding
import com.bumptech.glide.Glide

class ItineraryAdapter(
    private val onClick: (ItineraryEntity) -> Unit
) : ListAdapter<ItineraryEntity, ItineraryAdapter.ViewHolder>(ItineraryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDestinationRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itinerary = getItem(position)
        holder.bind(itinerary)
        holder.itemView.setOnClickListener {
            onClick(itinerary)
        }
    }

    class ViewHolder(private val binding: ItemDestinationRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itinerary: ItineraryEntity) {
            binding.tvNameDestination.text = itinerary.name
            binding.tvPlaceDestination.text = itinerary.location
            binding.ratingBar.rating = itinerary.popularity.toFloat()
            binding.tvTypeDestination.text = itinerary.type

            Glide.with(this@ViewHolder.itemView.context)
                .load(itinerary.image)
                .into(binding.ivDestination)
        }
    }

    class ItineraryDiffCallback : DiffUtil.ItemCallback<ItineraryEntity>() {
        override fun areItemsTheSame(oldItem: ItineraryEntity, newItem: ItineraryEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItineraryEntity, newItem: ItineraryEntity): Boolean {
            return oldItem == newItem
        }
    }
}

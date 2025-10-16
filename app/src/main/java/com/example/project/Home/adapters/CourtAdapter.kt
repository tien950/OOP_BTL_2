package com.example.project.Home.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project.Home.models.Court
import com.example.project.Home.activities.DetailActivity
import com.example.project.databinding.ListItemBinding

class CourtAdapter(private val courtList: List<Court>) :
    RecyclerView.Adapter<CourtAdapter.CourtViewHolder>() {

    inner class CourtViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(court: Court) {
            binding.title.text = court.getDisplayName()
            binding.address.text = court.getDisplayAddress()

            // Set click listener on the MaterialCardView root
            binding.root.setOnClickListener { view ->
                val intent = Intent(view.context, DetailActivity::class.java).apply {
                    putExtra("court_name", court.getDisplayName())
                    putExtra("court_address", court.getDisplayAddress())
                }
                view.context.startActivity(intent)
            }

            // Make sure the card is clickable
            binding.root.isClickable = true
            binding.root.isFocusable = true

            // Handle specific buttons
            binding.btnHeart.setOnClickListener {
                // Handle heart button click
            }

            binding.btnDirections.setOnClickListener {
                // Handle map/directions button click
            }

            binding.btnBook.setOnClickListener {
                // Handle booking button click
            }

            binding.phone.setOnClickListener {
                // Handle phone call functionality
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourtViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourtViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourtViewHolder, position: Int) {
        holder.bind(courtList[position])
    }

    override fun getItemCount(): Int = courtList.size
}

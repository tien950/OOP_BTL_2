package com.example.project.Home.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project.Home.activities.BookingActivity
import com.example.project.Home.activities.DetailActivity
import com.example.project.Home.models.Court
import com.example.project.databinding.ListItemBinding

class SearchAdapter(private val courtList: List<Court>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(court: Court) {
            binding.title.text = court.getDisplayName()
            binding.address.text = court.getDisplayAddress()

            // Set click listener on the entire card to navigate to DetailActivity
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

            // Click listener for "ĐẶT LỊCH" button
            binding.btnBook.setOnClickListener {
                val context = it.context
                val intent = Intent(context, BookingActivity::class.java).apply {
                    putExtra("COURT_ID", court.id)
                    putExtra("COURT_NAME", court.getDisplayName())
                    putExtra("COURT_ADDRESS", court.getDisplayAddress())
                }
                context.startActivity(intent)
            }

            // Handle other buttons
            binding.btnHeart.setOnClickListener {
                // Handle favorite functionality
            }

            binding.btnDirections.setOnClickListener {
                // Handle directions functionality
            }

            binding.phone.setOnClickListener {
                // Handle phone functionality
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(courtList[position])
    }

    override fun getItemCount(): Int = courtList.size
}

package com.example.project.data.repository

import com.example.project.Home.models.Court
import com.example.project.data.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VenueRepository {
    private val apiService = RetrofitClient.venueApiService

    suspend fun getVenues(): Result<List<Court>> {
        return withContext(Dispatchers.IO) {
            try {
                val venues = apiService.getVenues()
                Result.success(venues)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}


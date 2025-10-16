package com.example.project.data.api

import com.example.project.Home.models.Court
import retrofit2.http.GET

interface VenueApiService {
    @GET("api/venues")
    suspend fun getVenues(): List<Court>
}


package com.example.project.data.api

import com.example.project.data.model.VenueResponse
import retrofit2.Call
import retrofit2.http.GET

interface CourtApiService {
    @GET("api/venues")
    fun getCourts(): Call<VenueResponse>
}


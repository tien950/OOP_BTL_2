package com.example.project.Home.api

import com.example.project.Home.models.VenueResponse
import retrofit2.Call
import retrofit2.http.GET

interface CourtApiService {
    @GET("api/venues")
    fun getCourts(): Call<VenueResponse>
}

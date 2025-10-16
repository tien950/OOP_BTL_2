package com.example.project.Home.models

import com.google.gson.annotations.SerializedName

data class VenueResponse(
    @SerializedName("success")
    val success: Boolean = false,

    @SerializedName("data")
    val data: List<Court>? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("timestamp")
    val timestamp: String? = null
) {
    fun getCourtList(): List<Court> {
        return if (success) data ?: emptyList() else emptyList()
    }

    fun isSuccessful(): Boolean {
        return success
    }
}

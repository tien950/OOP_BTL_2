package com.example.project.Home.models

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("provinceOrCity")
    val provinceOrCity: String = "",

    @SerializedName("district")
    val district: String = "",

    @SerializedName("detailAddress")
    val detailAddress: String = ""
) {
    fun getFullAddress(): String {
        return buildString {
            if (detailAddress.isNotEmpty()) append(detailAddress)
            if (district.isNotEmpty()) {
                if (this.isNotEmpty()) append(", ")
                append(district)
            }
            if (provinceOrCity.isNotEmpty()) {
                if (this.isNotEmpty()) append(", ")
                append(provinceOrCity)
            }
        }
    }
}

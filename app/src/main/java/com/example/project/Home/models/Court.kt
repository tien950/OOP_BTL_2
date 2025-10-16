package com.example.project.Home.models

import com.google.gson.annotations.SerializedName

data class Court(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("numberOfCourt")
    val numberOfCourt: Int = 0,

    @SerializedName("courtsCount")
    val courtsCount: Int = 0,

    @SerializedName("address")
    val address: Address? = null,

    // Backup string address field if needed
    @SerializedName("addressString")
    val addressString: String? = null,

    @SerializedName("imageUrl")
    val imageUrl: String? = "",

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("photo")
    val photo: String? = null,

    @SerializedName("price")
    val price: Double? = null,

    @SerializedName("pricePerHour")
    val pricePerHour: Double? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("isActive")
    val isActive: Boolean? = true
) {
    // Get the best available name
    fun getDisplayName(): String {
        return name.ifEmpty { "Sân cầu lông" }
    }

    // Get the full address from Address object
    fun getDisplayAddress(): String {
        return address?.getFullAddress() ?: addressString ?: "Địa chỉ chưa cập nhật"
    }

    // Get the best available image
    fun getDisplayImage(): String {
        return imageUrl ?: image ?: photo ?: ""
    }

    // Get court count info
    fun getCourtInfo(): String {
        return when {
            numberOfCourt > 0 -> "Số sân: $numberOfCourt"
            courtsCount > 0 -> "Số sân: $courtsCount"
            else -> ""
        }
    }
}

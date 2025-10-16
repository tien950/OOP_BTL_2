package com.example.project.data.model

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
    fun getDisplayName(): String {
        return name.ifEmpty { "Sân cầu lông" }
    }

    fun getDisplayAddress(): String {
        return address?.getFullAddress() ?: addressString ?: "Địa chỉ chưa cập nhật"
    }

    fun getDisplayImage(): String {
        return imageUrl ?: image ?: photo ?: ""
    }

    fun getCourtInfo(): String {
        return when {
            numberOfCourt > 0 -> "Số sân: $numberOfCourt"
            courtsCount > 0 -> "Số sân: $courtsCount"
            else -> ""
        }
    }
}


package com.example.project.repository

import android.util.Log
import com.example.project.Home.models.Address
import com.example.project.Home.models.Court
import com.example.project.Home.models.VenueResponse
import com.example.project.core.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourtRepository {

    private val apiService = NetworkModule.getCourtApiService()

    interface CourtCallback {
        fun onSuccess(courts: List<Court>)
        fun onError(error: String)
    }

    fun getCourts(callback: CourtCallback) {
        Log.d(TAG, "=== BẮT ĐẦU GỌI API ===")

        apiService.getCourts().enqueue(object : Callback<VenueResponse> {
            override fun onResponse(call: Call<VenueResponse>, response: Response<VenueResponse>) {
                Log.d(TAG, "API Response - Code: ${response.code()}")

                if (response.isSuccessful) {
                    val venueResponse = response.body()

                    if (venueResponse != null && venueResponse.isSuccessful()) {
                        val courtList = venueResponse.getCourtList()
                        Log.d(TAG, "✅ Parsed successfully: ${courtList.size} courts")

                        if (courtList.isNotEmpty()) {
                            callback.onSuccess(courtList)
                        } else {
                            callback.onError("Không có sân nào")
                        }
                    } else {
                        Log.e(TAG, "Spring Boot response indicates failure")
                        callback.onError(venueResponse?.message ?: "API response failed")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "HTTP Error: ${response.code()}, $errorBody")
                    callback.onError("Server error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<VenueResponse>, t: Throwable) {
                Log.e(TAG, "Network Error: ${t.message}", t)

                val errorMessage = when (t) {
                    is java.net.ConnectException -> "Không thể kết nối server"
                    is java.net.UnknownHostException -> "Không tìm thấy server"
                    else -> "Lỗi: ${t.message}"
                }
                callback.onError(errorMessage)
            }
        })
    }

    fun getFallbackData(): List<Court> {
        return listOf(
            Court(
                id = 1,
                name = "Sân cầu lông Hoàng Anh",
                numberOfCourt = 4,
                address = Address(
                    id = 1,
                    provinceOrCity = "TP.HCM",
                    district = "Quận 5",
                    detailAddress = "123 Nguyễn Văn Cừ"
                )
            ),
            Court(
                id = 2,
                name = "Sân cầu lông Thiên Phúc",
                numberOfCourt = 6,
                address = Address(
                    id = 2,
                    provinceOrCity = "TP.HCM",
                    district = "Quận 9",
                    detailAddress = "456 Lê Văn Việt"
                )
            ),
            Court(
                id = 3,
                name = "Sân cầu lông Minh Khai",
                numberOfCourt = 3,
                address = Address(
                    id = 3,
                    provinceOrCity = "TP.HCM",
                    district = "Gò Vấp",
                    detailAddress = "789 Phan Văn Trị"
                )
            ),
            Court(
                id = 4,
                name = "Sân cầu lông Golden",
                numberOfCourt = 5,
                address = Address(
                    id = 4,
                    provinceOrCity = "TP.HCM",
                    district = "Quận 1",
                    detailAddress = "321 Điện Biên Phủ"
                )
            )
        )
    }

    companion object {
        private const val TAG = "CourtRepository"
    }
}


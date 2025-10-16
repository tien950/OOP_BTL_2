package com.example.project.Home.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.Home.adapters.SearchAdapter
import com.example.project.Home.api.CourtApiService
import com.example.project.Home.models.Address
import com.example.project.Home.models.Court
import com.example.project.Home.models.VenueResponse
import com.example.project.R
import com.example.project.databinding.HomeActivityBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(this, "Đang tải dữ liệu sân...", Toast.LENGTH_SHORT).show()

        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupEventListeners()
    }

    private fun setupRecyclerView() {
        // Hiển thị dữ liệu mẫu trước
        showFallbackData()

        // Sau đó thử gọi API
        loadCourtsFromApi()
    }

    private fun loadCourtsFromApi() {
        Log.d("HomeActivity", "=== BẮT ĐẦU GỌI API ===")
        Log.d("HomeActivity", "URL: https://0p1107w7-8080.asse.devtunnels.ms/api/venues")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://0p1107w7-8080.asse.devtunnels.ms/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val courtApi = retrofit.create(CourtApiService::class.java)

        // Gọi API với models đã được cập nhật cho Spring Boot
        courtApi.getCourts().enqueue(object : Callback<VenueResponse> {
            override fun onResponse(call: Call<VenueResponse>, response: Response<VenueResponse>) {
                Log.d("HomeActivity", "API Response - Code: ${response.code()}")
                Log.d("HomeActivity", "Response URL: ${response.raw().request.url}")

                if (response.isSuccessful) {
                    val venueResponse = response.body()
                    Log.d("HomeActivity", "Spring Boot Response: $venueResponse")

                    if (venueResponse != null && venueResponse.isSuccessful()) {
                        val courtList = venueResponse.getCourtList()
                        Log.d("HomeActivity", "✅ Parsed successfully: ${courtList.size} courts")

                        if (courtList.isNotEmpty()) {
                            // Log first court details
                            val firstCourt = courtList[0]
                            Log.d("HomeActivity", "First court name: ${firstCourt.getDisplayName()}")
                            Log.d("HomeActivity", "First court address: ${firstCourt.getDisplayAddress()}")
                            Log.d("HomeActivity", "First court info: ${firstCourt.getCourtInfo()}")

                            setupRecyclerViewWithData(courtList)
                            Toast.makeText(this@HomeActivity, "✅ Đã tải ${courtList.size} sân từ server", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.w("HomeActivity", "Server response successful but no courts found")
                            Toast.makeText(this@HomeActivity, "⚠️ Không có sân nào", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.e("HomeActivity", "Spring Boot response indicates failure")
                        Log.e("HomeActivity", "Message: ${venueResponse?.message}")
                        Toast.makeText(this@HomeActivity, "❌ ${venueResponse?.message ?: "API response failed"}", Toast.LENGTH_LONG).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("HomeActivity", "HTTP Error: ${response.code()}")
                    Log.e("HomeActivity", "Error body: $errorBody")
                    Toast.makeText(this@HomeActivity, "❌ Server error: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<VenueResponse>, t: Throwable) {
                Log.e("HomeActivity", "Network Error: ${t.message}")
                Log.e("HomeActivity", "Exception: ", t)

                when (t) {
                    is java.net.ConnectException -> {
                        Toast.makeText(this@HomeActivity, "🌐 Không thể kết nối server", Toast.LENGTH_LONG).show()
                    }
                    is java.net.UnknownHostException -> {
                        Toast.makeText(this@HomeActivity, "🔍 Không tìm thấy server", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(this@HomeActivity, "❌ Lỗi: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun showFallbackData() {
        Log.d("HomeActivity", "Hiển thị dữ liệu mẫu")
        setupRecyclerViewWithData(createFallbackData())
    }

    private fun setupRecyclerViewWithData(courtList: List<Court>) {
        Log.d("HomeActivity", "Cài đặt RecyclerView với ${courtList.size} sân")

        val courtAdapter = SearchAdapter(courtList)
        binding.rvCourts.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = courtAdapter
        }
    }

    private fun createFallbackData(): List<Court> {
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

    private fun setupEventListeners() {
        binding.edtSearch.isFocusable = false
        binding.edtSearch.isClickable = true

        binding.edtSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        // Thêm click listener cho icon search
        binding.ivSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        val cardFilter = findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardFilter)
        cardFilter.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            startActivity(intent)
        }
    }
}

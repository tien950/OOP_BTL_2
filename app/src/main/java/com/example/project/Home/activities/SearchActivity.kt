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
import com.example.project.databinding.ActivitySearchBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBackArrow()
        setupRecyclerView()
        setupSearchFocus()
        setupFilterClick()
    }

    private fun setupBackArrow() {
        binding.ivArrowBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        // Hiển thị dữ liệu mẫu trước
        showFallbackData()

        // Sau đó thử gọi API
        loadCourtsFromApi()
    }

    private fun loadCourtsFromApi() {
        Log.d("SearchActivity", "=== BẮT ĐẦU GỌI API ===")
        Log.d("SearchActivity", "URL: https://0p1107w7-8080.asse.devtunnels.ms/api/venues")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://0p1107w7-8080.asse.devtunnels.ms/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val courtApi = retrofit.create(CourtApiService::class.java)

        courtApi.getCourts().enqueue(object : Callback<VenueResponse> {
            override fun onResponse(call: Call<VenueResponse>, response: Response<VenueResponse>) {
                Log.d("SearchActivity", "API Response - Code: ${response.code()}")

                if (response.isSuccessful) {
                    val venueResponse = response.body()
                    Log.d("SearchActivity", "Spring Boot Response: $venueResponse")

                    if (venueResponse != null && venueResponse.isSuccessful()) {
                        val courtList = venueResponse.getCourtList()
                        Log.d("SearchActivity", "✅ Parsed successfully: ${courtList.size} courts")

                        if (courtList.isNotEmpty()) {
                            val firstCourt = courtList[0]
                            Log.d("SearchActivity", "First court name: ${firstCourt.getDisplayName()}")
                            Log.d("SearchActivity", "First court address: ${firstCourt.getDisplayAddress()}")

                            setupRecyclerViewWithData(courtList)
                            Toast.makeText(this@SearchActivity, "✅ Đã tải ${courtList.size} sân từ server", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.w("SearchActivity", "Server response successful but no courts found")
                            Toast.makeText(this@SearchActivity, "⚠️ Không có sân nào", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.e("SearchActivity", "Spring Boot response indicates failure")
                        Log.e("SearchActivity", "Message: ${venueResponse?.message}")
                        Toast.makeText(this@SearchActivity, "❌ ${venueResponse?.message ?: "API response failed"}", Toast.LENGTH_LONG).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("SearchActivity", "HTTP Error: ${response.code()}")
                    Log.e("SearchActivity", "Error body: $errorBody")
                    Toast.makeText(this@SearchActivity, "❌ Server error: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<VenueResponse>, t: Throwable) {
                Log.e("SearchActivity", "Network Error: ${t.message}")
                Log.e("SearchActivity", "Exception: ", t)

                when (t) {
                    is java.net.ConnectException -> {
                        Toast.makeText(this@SearchActivity, "🌐 Không thể kết nối server", Toast.LENGTH_LONG).show()
                    }
                    is java.net.UnknownHostException -> {
                        Toast.makeText(this@SearchActivity, "🔍 Không tìm thấy server", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(this@SearchActivity, "❌ Lỗi: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun showFallbackData() {
        Log.d("SearchActivity", "Hiển thị dữ liệu mẫu")
        setupRecyclerViewWithData(createFallbackData())
    }

    private fun setupRecyclerViewWithData(courtList: List<Court>) {
        Log.d("SearchActivity", "Cài đặt RecyclerView với ${courtList.size} sân")

        val searchAdapter = SearchAdapter(courtList)
        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchAdapter
        }
    }

    private fun createFallbackData(): List<Court> {
        return listOf(
            Court(
                id = 1,
                name = "New Sports",
                numberOfCourt = 4,
                address = Address(
                    id = 1,
                    provinceOrCity = "TP.HCM",
                    district = "Quận 1",
                    detailAddress = "38 Bích Câu"
                )
            ),
            Court(
                id = 2,
                name = "3CE",
                numberOfCourt = 6,
                address = Address(
                    id = 2,
                    provinceOrCity = "TP.HCM",
                    district = "Quận 1",
                    detailAddress = "85 Tôn Đức Thắng"
                )
            ),
            Court(
                id = 3,
                name = "Pickle Pit Tôn Đức Thắng",
                numberOfCourt = 3,
                address = Address(
                    id = 3,
                    provinceOrCity = "TP.HCM",
                    district = "Gò Vấp",
                    detailAddress = "1 P. Phan Văn Trị"
                )
            ),
            Court(
                id = 4,
                name = "Thịnh Hào Pickleball",
                numberOfCourt = 5,
                address = Address(
                    id = 4,
                    provinceOrCity = "TP.HCM",
                    district = "Hai Bà Trưng",
                    detailAddress = "Số 60, ngõ Thịnh Hào 1"
                )
            )
        )
    }

    private fun setupSearchFocus() {
        // Tự động focus vào EditText và hiện keyboard
        binding.edtSearch.requestFocus()

        // Hiển thị keyboard tự động
        val imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.showSoftInput(binding.edtSearch, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT)
    }

    private fun setupFilterClick() {
        binding.ivFilter.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            startActivity(intent)
        }
    }
}

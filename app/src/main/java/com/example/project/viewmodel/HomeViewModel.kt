package com.example.project.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.Home.models.Court
import com.example.project.data.repository.VenueRepository
import com.example.project.data.SampleData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: VenueRepository = VenueRepository()
) : ViewModel() {
    private val _courts = MutableStateFlow<List<Court>>(emptyList())
    val courts: StateFlow<List<Court>> = _courts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadCourts()
    }

    fun loadCourts() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.getVenues()
                .onSuccess { venues ->
                    if (venues.isNotEmpty()) {
                        _courts.value = venues
                        _errorMessage.value = null
                        Log.d("HomeViewModel", "✅ Loaded ${venues.size} venues from API")
                    } else {
                        // Nếu API trả về rỗng, sử dụng dữ liệu mẫu
                        _courts.value = SampleData.sampleCourts
                        Log.d("HomeViewModel", "ℹ️ API returned empty, using ${SampleData.sampleCourts.size} sample courts")
                    }
                }
                .onFailure { exception ->
                    Log.e("HomeViewModel", "❌ Error loading venues: ${exception.message}", exception)

                    // Sử dụng dữ liệu mẫu khi API thất bại
                    _courts.value = SampleData.sampleCourts
                    Log.d("HomeViewModel", "ℹ️ Using ${SampleData.sampleCourts.size} sample courts due to API error")

                    _errorMessage.value = when (exception) {
                        is java.net.ConnectException -> "Không thể kết nối đến server - Hiển thị dữ liệu mẫu"
                        is java.net.UnknownHostException -> "Không tìm thấy server - Hiển thị dữ liệu mẫu"
                        is java.net.SocketTimeoutException -> "Kết nối bị timeout - Hiển thị dữ liệu mẫu"
                        else -> "Lỗi: ${exception.message ?: "Không xác định"} - Hiển thị dữ liệu mẫu"
                    }
                }

            _isLoading.value = false
        }
    }

    fun refreshCourts() {
        loadCourts()
    }
}

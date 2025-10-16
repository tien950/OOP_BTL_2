package com.example.project.core

object Constants {
    // API Configuration
    const val BASE_URL = "https://0p1107w7-8080.asse.devtunnels.ms/"
    const val API_VENUES = "api/venues"

    // Timing
    const val SPLASH_DELAY = 2000L

    // Prices
    const val PRICE_PER_HOUR = 100_000
    const val SLOT_DURATION_HOURS = 0.5

    // UI Messages
    object Messages {
        const val LOADING_DATA = "Đang tải dữ liệu sân..."
        const val LOAD_SUCCESS = "✅ Đã tải %d sân từ server"
        const val NO_COURTS = "⚠️ Không có sân nào"
        const val API_FAILED = "❌ %s"
        const val SERVER_ERROR = "❌ Server error: %d"
        const val CONNECTION_ERROR = "🌐 Không thể kết nối server"
        const val HOST_ERROR = "🔍 Không tìm thấy server"
        const val GENERIC_ERROR = "❌ Lỗi: %s"
    }
}


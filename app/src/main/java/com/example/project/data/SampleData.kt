package com.example.project.data

import com.example.project.Home.models.Address
import com.example.project.Home.models.Court

object SampleData {
    // Danh sách sân mẫu để test
    val sampleCourts = listOf(
        Court(
            id = 1,
            name = "Sân Cầu Lông Thiên Đức",
            numberOfCourt = 8,
            courtsCount = 8,
            address = Address(
                id = 1,
                detailAddress = "123 Đường Láng",
                district = "Đống Đa",
                provinceOrCity = "Hà Nội"
            ),
            imageUrl = "https://example.com/court1.jpg",
            price = 80.0,
            pricePerHour = 80.0,
            status = "available",
            isActive = true
        ),
        Court(
            id = 2,
            name = "Sân Cầu Lông Hoàng Gia",
            numberOfCourt = 12,
            courtsCount = 12,
            address = Address(
                id = 2,
                detailAddress = "456 Giải Phóng",
                district = "Hai Bà Trưng",
                provinceOrCity = "Hà Nội"
            ),
            imageUrl = "https://example.com/court2.jpg",
            price = 100.0,
            pricePerHour = 100.0,
            status = "available",
            isActive = true
        ),
        Court(
            id = 3,
            name = "Sân Cầu Lông Minh Khai",
            numberOfCourt = 6,
            courtsCount = 6,
            address = Address(
                id = 3,
                detailAddress = "789 Trần Duy Hưng",
                district = "Cầu Giấy",
                provinceOrCity = "Hà Nội"
            ),
            imageUrl = "https://example.com/court3.jpg",
            price = 70.0,
            pricePerHour = 70.0,
            status = "available",
            isActive = true
        ),
        Court(
            id = 4,
            name = "Sân Cầu Lông Thăng Long",
            numberOfCourt = 10,
            courtsCount = 10,
            address = Address(
                id = 4,
                detailAddress = "321 Nguyễn Trãi",
                district = "Thanh Xuân",
                provinceOrCity = "Hà Nội"
            ),
            imageUrl = "https://example.com/court4.jpg",
            price = 90.0,
            pricePerHour = 90.0,
            status = "available",
            isActive = true
        ),
        Court(
            id = 5,
            name = "Sân Cầu Lông Phương Đông",
            numberOfCourt = 5,
            courtsCount = 5,
            address = Address(
                id = 5,
                detailAddress = "555 Phạm Văn Đồng",
                district = "Bắc Từ Liêm",
                provinceOrCity = "Hà Nội"
            ),
            imageUrl = "https://example.com/court5.jpg",
            price = 75.0,
            pricePerHour = 75.0,
            status = "available",
            isActive = true
        )
    )

    // Hàm để lấy danh sách sân theo khu vực (có thể mở rộng sau)
    fun getCourtsByArea(area: String): List<Court> {
        return sampleCourts.filter { it.address?.district?.contains(area, ignoreCase = true) == true }
    }

    // Hàm để lấy sân theo ID
    fun getCourtById(id: Int): Court? {
        return sampleCourts.find { it.id == id }
    }
}

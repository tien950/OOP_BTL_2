package com.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project.Home.models.Court
import com.example.project.ui.theme.ColorPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    court: Court,
    onBackClick: () -> Unit,
    onBookClick: (Court) -> Unit
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(ColorPrimary.copy(alpha = 0.3f))
            ) {
                // Action buttons overlay
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp, start = 20.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color.White, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color.White, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Directions,
                                contentDescription = "Directions",
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color.White, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Button(
                            onClick = { onBookClick(court) },
                            colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Text("Đặt lịch", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Court Name
            Text(
                text = court.getDisplayName(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Address
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = ColorPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = court.getDisplayAddress(),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Info Cards
            InfoCard(
                title = "Số sân",
                value = court.getCourtInfo()
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoCard(
                title = "Giá",
                value = court.price?.let { "${it.toInt()},000 VNĐ/giờ" } ?: "Liên hệ"
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoCard(
                title = "Trạng thái",
                value = court.status ?: "Đang hoạt động"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Description
            Text(
                text = "Mô tả",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sân cầu lông chất lượng cao với đầy đủ tiện nghi, không gian thoáng mát, giá cả phải chăng. Phù hợp cho cả người mới và chuyên nghiệp.",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Book Button
            Button(
                onClick = { onBookClick(court) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary)
            ) {
                Text("Đặt sân ngay", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun InfoCard(title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, fontWeight = FontWeight.Medium)
            Text(text = value, color = ColorPrimary, fontWeight = FontWeight.Bold)
        }
    }
}


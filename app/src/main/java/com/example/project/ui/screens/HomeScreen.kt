package com.example.project.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.Home.models.Court
import com.example.project.ui.theme.*
import com.example.project.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCourtClick: (Court) -> Unit,
    onSearchClick: () -> Unit,
    onFilterClick: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val courts by viewModel.courts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header Section
        item {
            HeaderSection(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onSearchClick = onSearchClick
            )
        }

        // Category Chips
        item {
            CategoryChips()
        }

        // Filter Section
        item {
            FilterSection(onFilterClick = onFilterClick)
        }

        // Courts List
        if (isLoading && courts.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        // Error Message
        errorMessage?.let { error ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
                ) {
                    Text(
                        text = "⚠️ $error",
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFFD32F2F)
                    )
                }
            }
        }

        // Courts Grid
        items(courts) { court ->
            CourtCard(
                court = court,
                onCourtClick = { onCourtClick(court) }
            )
        }
    }
}

@Composable
fun HeaderSection(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientEnd)
                )
            )
            .statusBarsPadding()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 16.dp)
    ) {
        // Avatar and Name Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.3f))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                    Text(
                        text = currentDate,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Xin chào!",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Notification Icon
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(36.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Thông báo",
                    tint = ColorPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search Bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable { onSearchClick() },
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Logo",
                    tint = ColorPrimary
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Tìm kiếm",
                    color = Color.Gray,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryChips() {
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listOf("Cầu lông gần tôi", "Pickleball gần tôi", "Vé của tôi", "Sân đã lưu")) { category ->
            AssistChip(
                onClick = { },
                label = { Text(category) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color.LightGray.copy(alpha = 0.3f)
                )
            )
        }
    }
}

@Composable
fun FilterSection(onFilterClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onFilterClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Bộ lọc",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Filter"
            )
        }
    }
}

@Composable
fun CourtCard(
    court: Court,
    onCourtClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onCourtClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Image Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(ColorPrimary.copy(alpha = 0.2f))
            ) {
                // Placeholder for image
            }

            // Info Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logo/Avatar
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(ColorPrimary.copy(alpha = 0.3f))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = court.getDisplayName(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = court.getDisplayAddress(),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = court.getCourtInfo(),
                        fontSize = 12.sp,
                        color = ColorPrimary
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Call"
                        )
                    }
                    Button(
                        onClick = onCourtClick,
                        colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text("Đặt sân", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

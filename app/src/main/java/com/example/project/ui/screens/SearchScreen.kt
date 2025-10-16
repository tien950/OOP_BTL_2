package com.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project.Home.models.Court
import com.example.project.ui.theme.ColorPrimary
import com.example.project.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onCourtClick: (Court) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val courts by viewModel.courts.collectAsState()

    val filteredCourts = remember(searchQuery, courts) {
        if (searchQuery.isEmpty()) {
            courts
        } else {
            courts.filter {
                it.getDisplayName().contains(searchQuery, ignoreCase = true) ||
                        it.getDisplayAddress().contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tìm kiếm") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ColorPrimary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Tìm kiếm sân...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(28.dp)
            )

            // Results
            if (filteredCourts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = "No results",
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Không tìm thấy kết quả",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                }
            } else {
                LazyColumn {
                    items(filteredCourts) { court ->
                        SearchResultItem(
                            court = court,
                            onCourtClick = { onCourtClick(court) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultItem(
    court: Court,
    onCourtClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onCourtClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(ColorPrimary.copy(alpha = 0.3f), CircleShape)
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
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Go",
                tint = Color.Gray
            )
        }
    }
}


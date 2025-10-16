package com.example.project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.project.Home.models.Court
import com.example.project.ui.theme.DarkBlue

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Trang chủ", Icons.Default.Home)
    object Account : BottomNavItem("account", "Tài khoản", Icons.Default.AccountCircle)
}

@Composable
fun MainScreen(
    onCourtClick: (Court) -> Unit,
    onSearchClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Account
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = DarkBlue,
                modifier = Modifier.height(100.dp)
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Column(
                                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (selectedTab == index) DarkBlue else androidx.compose.ui.graphics.Color.White
                                )
                            }
                        },
                        label = null,
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DarkBlue,
                            selectedTextColor = DarkBlue,
                            unselectedIconColor = androidx.compose.ui.graphics.Color.White,
                            unselectedTextColor = androidx.compose.ui.graphics.Color.White,
                            indicatorColor = androidx.compose.ui.graphics.Color.White
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> HomeScreen(
                    onCourtClick = onCourtClick,
                    onSearchClick = onSearchClick,
                    onFilterClick = onFilterClick
                )
                1 -> AccountScreen()
            }
        }
    }
}

@Composable
fun AccountScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text("Tài khoản", style = MaterialTheme.typography.headlineMedium)
    }
}

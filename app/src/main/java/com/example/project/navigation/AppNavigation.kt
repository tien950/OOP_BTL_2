package com.example.project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project.Home.models.Court
import com.example.project.ui.screens.*
import com.example.project.data.SampleData

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail")
    object Search : Screen("search")
    object Filter : Screen("filter")
    object Booking : Screen("booking")
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route
) {
    // Store selected court in a mutable state at navigation level
    var selectedCourt: Court? = null

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) {
            MainScreen(
                onCourtClick = { court ->
                    selectedCourt = court
                    navController.navigate(Screen.Detail.route)
                },
                onSearchClick = {
                    navController.navigate(Screen.Search.route)
                },
                onFilterClick = {
                    navController.navigate(Screen.Filter.route)
                }
            )
        }

        composable(Screen.Detail.route) {
            selectedCourt?.let { court ->
                DetailScreen(
                    court = court,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onBookClick = { bookingCourt ->
                        selectedCourt = bookingCourt
                        navController.navigate(Screen.Booking.route)
                    }
                )
            }
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onCourtClick = { court ->
                    selectedCourt = court
                    navController.navigate(Screen.Detail.route)
                }
            )
        }

        composable(Screen.Filter.route) {
            FilterScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onApplyFilter = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Booking.route) {
            selectedCourt?.let { court ->
                BookingScreen(
                    court = court,
                    availableCourts = SampleData.sampleCourts, // Truyền danh sách sân mẫu
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onConfirmBooking = {
                        // Handle booking confirmation
                        navController.popBackStack(Screen.Home.route, inclusive = false)
                    }
                )
            }
        }
    }
}

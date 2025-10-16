package com.example.project.ui.screens

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project.Home.models.Court
import com.example.project.ui.theme.ColorPrimary
import java.text.SimpleDateFormat
import java.util.*

// Data class để lưu thông tin ô được chọn (sân + giờ)
data class CourtTimeSlot(val courtNumber: Int, val timeSlot: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    court: Court,
    availableCourts: List<Court> = emptyList(),
    onBackClick: () -> Unit,
    onConfirmBooking: () -> Unit
) {
    var selectedDate by remember { mutableStateOf("") }
    var selectedSlots by remember { mutableStateOf(setOf<CourtTimeSlot>()) } // Lưu các ô đã chọn
    var playerName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )

    // Lấy số lượng sân
    val numberOfCourts = court.numberOfCourt.takeIf { it > 0 }
        ?: court.courtsCount.takeIf { it > 0 }
        ?: 1

    // Danh sách khung giờ
    val timeSlots = listOf(
        "00:00", "00:30", "01:00", "01:30", "02:00", "02:30",
        "03:00", "03:30", "04:00", "04:30", "05:00", "05:30",
        "06:00", "06:30", "07:00", "07:30", "08:00", "08:30",
        "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
        "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
        "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
        "18:00", "18:30", "19:00", "19:30", "20:00", "20:30",
        "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"
    )

    // DatePickerDialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        selectedDate = dateFormat.format(Date(millis))
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Hủy")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Đặt sân") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Court Info
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = ColorPrimary.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = court.getDisplayName(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = court.getDisplayAddress(),
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Date Selection
            Text(
                text = "Chọn ngày",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = selectedDate.ifEmpty { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()) },
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.CalendarToday, contentDescription = "Date")
                },
                placeholder = { Text("Chọn ngày") },
                readOnly = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                interactionSource = remember { MutableInteractionSource() }
                    .also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Release) {
                                    showDatePicker = true
                                }
                            }
                        }
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Booking Grid Table
            Text(
                text = "Chọn sân và giờ (nhấn vào ô trong bảng)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Grid Table with fixed first column
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    // Fixed Column - Tên sân (cố định, không cuộn)
                    Column {
                        // Header cell - "Sân"
                        Box(
                            modifier = Modifier
                                .width(70.dp)
                                .height(50.dp)
                                .border(1.dp, Color.Gray)
                                .background(ColorPrimary.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Sân",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        // Các hàng tên sân
                        for (courtNum in 1..numberOfCourts) {
                            Box(
                                modifier = Modifier
                                    .width(70.dp)
                                    .height(45.dp)
                                    .border(1.dp, Color.Gray)
                                    .background(Color(0xFFF5F5F5)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Sân $courtNum",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    // Scrollable Column - Các khung giờ (có thể cuộn ngang)
                    Column(
                        modifier = Modifier.horizontalScroll(rememberScrollState())
                    ) {
                        // Header Row - Các khung giờ
                        Row {
                            timeSlots.forEach { time ->
                                Box(
                                    modifier = Modifier
                                        .width(80.dp)
                                        .height(50.dp)
                                        .border(1.dp, Color.Gray)
                                        .background(ColorPrimary.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = time,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        // Data Rows - Các ô chọn giờ
                        for (courtNum in 1..numberOfCourts) {
                            Row {
                                timeSlots.forEach { time ->
                                    val slot = CourtTimeSlot(courtNum, time)
                                    val isSelected = selectedSlots.contains(slot)

                                    Box(
                                        modifier = Modifier
                                            .width(80.dp)
                                            .height(45.dp)
                                            .border(1.dp, Color.Gray)
                                            .background(
                                                if (isSelected) ColorPrimary
                                                else Color.White
                                            )
                                            .clickable {
                                                selectedSlots = if (isSelected) {
                                                    selectedSlots - slot
                                                } else {
                                                    selectedSlots + slot
                                                }
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isSelected) {
                                            Text(
                                                text = "✓",
                                                color = Color.White,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Thông tin đã chọn
            if (selectedSlots.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = ColorPrimary.copy(alpha = 0.1f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Đã chọn ${selectedSlots.size} ô:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        // Nhóm theo sân
                        selectedSlots.groupBy { it.courtNumber }.forEach { (courtNum, slots) ->
                            Text(
                                text = "• Sân $courtNum: ${slots.map { it.timeSlot }.sorted().joinToString(", ")}",
                                fontSize = 12.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Player Info
            Text(
                text = "Thông tin người đặt",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = playerName,
                onValueChange = { playerName = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Họ và tên") },
                placeholder = { Text("Nhập họ và tên") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Số điện thoại") },
                placeholder = { Text("Nhập số điện thoại") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Price Summary
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Giá sân/giờ:")
                        Text(
                            text = court.price?.let { "${it.toInt()},000 VNĐ" } ?: "Liên hệ",
                            fontWeight = FontWeight.Bold,
                            color = ColorPrimary
                        )
                    }

                    if (selectedSlots.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Tổng số ô đã chọn:")
                            Text(
                                text = "${selectedSlots.size} ô",
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Tổng tiền:",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = court.price?.let {
                                    val total = it.toInt() * selectedSlots.size
                                    "${total},000 VNĐ"
                                } ?: "Liên hệ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = ColorPrimary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Confirm Button
            Button(
                onClick = onConfirmBooking,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary),
                enabled = selectedSlots.isNotEmpty() && playerName.isNotEmpty() && phoneNumber.isNotEmpty()
            ) {
                Text("Xác nhận đặt sân", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

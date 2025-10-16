package com.example.project.Home.activities

import android.app.DatePickerDialog
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project.R
import java.text.SimpleDateFormat
import java.util.*

class BookingActivity : AppCompatActivity() {
    private val selectedSlots = mutableSetOf<Int>()
    private val pricePerHour = 100_000 // Giá mỗi giờ
    private lateinit var tvTotalHours: TextView
    private lateinit var tvTotalPrice: TextView
    private lateinit var summaryLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_booking)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvTotalHours = findViewById(R.id.tvTotalHours)
        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        summaryLayout = findViewById(R.id.summaryLayout)

        // Xử lý chọn ngày cho tvDatePicker
        val tvDatePicker = findViewById<TextView>(R.id.tvDatePicker)
        val ivArrowDown = findViewById<ImageView>(R.id.ivArrowDown)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()

        // Hiển thị ngày hiện tại
        tvDatePicker.text = dateFormat.format(calendar.time)

        // Hàm hiển thị DatePicker
        val showDatePicker = {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val dialog = DatePickerDialog(this, { _, y, m, d ->
                calendar.set(y, m, d)
                tvDatePicker.text = dateFormat.format(calendar.time)
            }, year, month, day)
            dialog.show()
        }

        // Bấm vào TextView hoặc ImageView đều hiện lịch
        tvDatePicker.setOnClickListener {
            showDatePicker()
        }

        ivArrowDown.setOnClickListener {
            showDatePicker()
        }

        // Thiết lập click listener cho tất cả các slot
        setupSlotClickListeners()

        // Đồng bộ cuộn giữa header thời gian và bảng lịch
        val timeHeaderScrollView = findViewById<HorizontalScrollView>(R.id.timeHeaderScrollView)
        val gridScrollView = findViewById<HorizontalScrollView>(R.id.gridScrollView)

        gridScrollView.setOnScrollChangeListener { _, scrollX, _, _, _ ->
            timeHeaderScrollView.scrollTo(scrollX, 0)
        }

        timeHeaderScrollView.setOnScrollChangeListener { _, scrollX, _, _, _ ->
            gridScrollView.scrollTo(scrollX, 0)
        }
    }

    private fun setupSlotClickListeners() {
        for (court in 1..3) {
            for (slot in 1..34) {
                val slotId = resources.getIdentifier("slot${court}_${slot}", "id", packageName)
                val slotView = findViewById<View>(slotId)
                slotView?.setOnClickListener { view ->
                    toggleSlotSelection(view, slotId)
                    updateSummary()
                }
            }
        }
    }

    private fun toggleSlotSelection(view: View, slotId: Int) {
        if (selectedSlots.contains(slotId)) {
            selectedSlots.remove(slotId)
            updateSlotBackground(view, false)
        } else {
            selectedSlots.add(slotId)
            updateSlotBackground(view, true)
        }
    }

    private fun updateSlotBackground(view: View, isSelected: Boolean) {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.cornerRadius = 1f * resources.displayMetrics.density

        if (isSelected) {
            // Màu xanh đậm khi được chọn
            drawable.setColor(ContextCompat.getColor(this, R.color.sky_400))
            drawable.setStroke(
                (0.5f * resources.displayMetrics.density).toInt(),
                ContextCompat.getColor(this, R.color.black)
            )
        } else {
            // Màu xanh nhạt khi không được chọn
            drawable.setColor(ContextCompat.getColor(this, R.color.slot_available))
            drawable.setStroke(
                (0.5f * resources.displayMetrics.density).toInt(),
                ContextCompat.getColor(this, R.color.black)
            )
        }

        view.background = drawable
    }

    private fun updateSummary() {
        if (selectedSlots.isEmpty()) {
            summaryLayout.visibility = View.GONE
        } else {
            summaryLayout.visibility = View.VISIBLE
            val totalSlots = selectedSlots.size
            val totalHours = totalSlots * 0.5 // mỗi slot 30 phút
            val totalPrice = (totalHours * pricePerHour).toInt()
            tvTotalHours.text = String.format("%.1f giờ", totalHours)
            tvTotalPrice.text = String.format("%,d VNĐ", totalPrice)
        }
    }
}
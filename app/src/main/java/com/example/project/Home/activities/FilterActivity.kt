package com.example.project.Home.activities

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project.R
import com.google.android.material.slider.Slider

class FilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_filter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupLocationTypeToggle()
    }

    private fun setupLocationTypeToggle() {
        val radioGroup = findViewById<RadioGroup>(R.id.rgLocationType)
        val layoutAreaSelection = findViewById<LinearLayout>(R.id.layoutAreaSelection)
        val layoutDistanceSelection = findViewById<LinearLayout>(R.id.layoutDistanceSelection)
        val sliderDistance = findViewById<Slider>(R.id.sliderDistance)
        val tvDistanceLabel = findViewById<TextView>(R.id.tvDistanceLabel)

        // Xử lý sự kiện thay đổi lựa chọn
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbByArea -> {
                    // Hiện phần khu vực, ẩn phần khoảng cách
                    layoutAreaSelection.visibility = View.VISIBLE
                    layoutDistanceSelection.visibility = View.GONE
                }
                R.id.rbByDistance -> {
                    // Ẩn phần khu vực, hiện phần khoảng cách
                    layoutAreaSelection.visibility = View.GONE
                    layoutDistanceSelection.visibility = View.VISIBLE
                }
            }
        }

        // Xử lý sự kiện thay đổi giá trị slider
        sliderDistance.addOnChangeListener { slider, value, fromUser ->
            tvDistanceLabel.text = "Khoảng cách: ${value.toInt()} km"
        }
    }
}
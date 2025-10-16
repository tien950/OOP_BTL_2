package com.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputBinding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*
import com.example.project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val edtNhapA = binding.edtNhapA
//        val edtNhapB = binding.edtNhapB
//        val edtKQ = binding.edtKQ
//        val btnCong = binding.btnCong
//        val btnReset = binding.btnReset
//        val btnNhan = binding.btnNhan
//        val btnChia = binding.btnChia
//        val btnDangXuat = binding.btnDangXuat
//
//        btnCong.setOnClickListener {
//            val a=edtNhapA.text.toString().toInt()
//            val b=edtNhapB.text.toString().toInt()
//            val kq=a+b
//            edtKQ.setText(kq.toString())
//            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
//        }
//
//        btnReset.setOnClickListener {
//            edtNhapA.setText("")
//            edtNhapB.setText("")
//            edtKQ.setText("Ket qua")
//            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
//        }
//
//        var chiaSe : View.OnClickListener? = null
//        chiaSe = View.OnClickListener {
//            if(it==btnNhan){
//                val a=edtNhapA.text.toString().toInt()
//                val b=edtNhapB.text.toString().toInt()
//                val kq=a*b
//                edtKQ.setText(kq.toString())
//                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
//            }
//            else if(it==btnChia){
//                val a=edtNhapA.text.toString().toDouble()
//                val b=edtNhapB.text.toString().toDouble()
//                val kq=a/b
//                edtKQ.setText(kq.toString())
//                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        btnNhan.setOnClickListener(chiaSe)
//        btnChia.setOnClickListener(chiaSe)
//
//        btnDangXuat.setOnClickListener {
//            Toast.makeText(this, "Dang xuat thanh cong", Toast.LENGTH_SHORT).show()
//            finish()
//        }
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}
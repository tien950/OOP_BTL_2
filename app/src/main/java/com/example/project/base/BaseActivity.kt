package com.example.project.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB

    protected fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    protected fun showError(message: String) {
        showToast(message, Toast.LENGTH_LONG)
    }

    protected fun showSuccess(message: String) {
        showToast(message, Toast.LENGTH_SHORT)
    }
}


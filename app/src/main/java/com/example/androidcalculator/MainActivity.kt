package com.example.androidcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.androidcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var canAddOperation = false
    private  var canAddDecimal = true
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun numberAction(view: View) {
        if (view is Button) {
            if (view.text == ".") {
                if (canAddDecimal) {
                    binding.workingTV.append(view.text)
                }
                canAddDecimal = false
            } else {
                binding.workingTV.append(view.text)
            }
            canAddOperation = true
        }
    }

    fun operationAction(view: View) {
        if (view is Button && canAddOperation) {
            binding.workingTV.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }

    fun allClearAction(view: View) {
        binding.workingTV.text = ""
        binding.resultTV.text = ""
    }
    fun backSpaceAction(view: View) {
        val workingTVText = binding.workingTV.text.toString()
        if (workingTVText.isNotEmpty()) {
            binding.workingTV.text = workingTVText.substring(0, workingTVText.length - 1)
        }
        binding.resultTV.text = ""
    }
    fun equalsAction(view: View) {}

}
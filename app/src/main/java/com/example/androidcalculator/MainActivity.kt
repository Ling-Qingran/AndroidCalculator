package com.example.androidcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var canAddOperation = false
    private  var canAddDecimal = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun numberAction(view: View) {
        if (view is Button) {
            if (view.text == ".") {
                if (canAddDecimal) {
                    workingTV.append(view.text)
                }
                canAddDecimal = false
            } else {
                workingTV.append(view.text)
            }
            canAddOperation = true
        }
    }

    fun operationAction(view: View) {
        if (view is Button && canAddOperation) {
            workingTV.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }

    fun allClearAction(view: View) {
        workingTV.text = ""
        resultTV.text = ""
    }
    fun backSpaceAction(view: View) {
        val workingTVText = workingTV.text.toString()
        if (workingTVText.isNotEmpty()) {
            workingTV.text = workingTVText.substring(0, workingTVText.length - 1)
        }
        resultTV.text = ""
    }
    fun equalsAction(view: View) {}

}
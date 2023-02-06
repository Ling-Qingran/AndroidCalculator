package com.example.androidcalculator

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.androidcalculator.databinding.ActivityMainBinding

class ResultViewModel:ViewModel() {
    private var canAddOperation:Boolean = false
    private  var canAddDecimal:Boolean = true
    private var workingTV:CharSequence=""
    private var resultTV:CharSequence=""


    // TODO: check
    val current_canAddOperation:Boolean
        get()=canAddOperation
    val current_canAddDecimal:Boolean
        get()=canAddDecimal
    val current_workingTV_text:CharSequence
        get()=workingTV
    val current_resultTV_text:CharSequence
        get()=resultTV

    fun setCanAddOperation(input:Boolean){
        canAddOperation=input
    }

    fun setCanAddDecimal(input: Boolean){
        canAddDecimal=input
    }

    fun allClearButton(){
        workingTV = ""
        resultTV = ""
    }

    fun setWorkingTV(input: CharSequence){
        workingTV=input
    }
    fun setResultTV(input: CharSequence){
        resultTV=input
    }

    fun workingTVAppend(input: CharSequence){
        var temp=workingTV.toString()
        temp+=input
        workingTV=temp
    }




}
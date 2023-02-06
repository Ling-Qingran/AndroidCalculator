package com.example.androidcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import com.example.androidcalculator.databinding.ActivityMainBinding
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val resultViewModel:ResultViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun numberAction(view: View) {
        if (view is Button) {
            if (view.text == ".") {
                if (resultViewModel.current_canAddDecimal) {
                    resultViewModel.workingTVAppend(view.text)
                    updateWorkingTV()
                }
                resultViewModel.setCanAddDecimal(false)
            } else {
                resultViewModel.workingTVAppend(view.text)
                updateWorkingTV()
            }
            resultViewModel.setCanAddOperation(true)
        }
    }

    fun operationAction(view: View) {
        if (view is Button && view.id == R.id.sqrt){
            resultViewModel.setResultTV(sqrtCalculate())
            updateResultTV()
        }
        if (view is Button && resultViewModel.current_canAddOperation) {
            resultViewModel.workingTVAppend(view.text)
            updateWorkingTV()
            resultViewModel.setCanAddOperation(false)
            resultViewModel.setCanAddDecimal(true)
        }
    }

    fun allClearAction(view: View) {
        resultViewModel.allClearButton()
        updateWorkingTV()
        updateResultTV()
    }
    fun backSpaceAction(view: View) {
        val workingTVText = resultViewModel.current_workingTV_text.toString()
        if (workingTVText.isNotEmpty()) {
            resultViewModel.setWorkingTV(workingTVText.substring(0, workingTVText.length - 1))
            updateWorkingTV()
        }
        resultViewModel.setResultTV("")
        updateResultTV()

    }
    fun equalsAction(view: View) {
        resultViewModel.setResultTV(calculation())
        resultViewModel.setWorkingTV("")
        resultViewModel.setCanAddOperation(false)
        updateWorkingTV()
        updateResultTV()
    }

    private fun calculation(): String {
        if (resultViewModel.current_workingTV_text.isEmpty())
            return "NaN"
        val breakList = breakText()
        if (breakList[breakList.lastIndex] is Char)
            return "NaN"

        var timeDivRes =breakList
        if (timeDivRes.contains('x') || timeDivRes.contains('/')) {
            timeDivRes = timeOrDivide(timeDivRes)
        }
        if (timeDivRes.isEmpty()) {
            return "NaN"
        }
        return plusOrMinus(timeDivRes)
    }

    private fun plusOrMinus(oldList: ArrayList<Any>): String {
        var i = 0
        var result = oldList[0] as Double
        while (i < oldList.size){
            if (oldList[i] is Char && i != oldList.lastIndex){
                val operator = oldList[i]
                val secondNum = oldList[i+1] as Double
                when(operator){
                    '+' ->{
                        result += secondNum
                    }
                    '-' ->{
                        result -= secondNum
                    }
                }
            }
            i++
        }
        return result.toString()
    }

    private fun timeOrDivide(oldList: ArrayList<Any>): ArrayList<Any> {
        var flag=false
        var newList = ArrayList<Any>()
        var i = 0
//        var currentIndex = oldList.size
        var currentResult=-1.0
        while (i < oldList.size){
            if (oldList[i] is Char && i != oldList.lastIndex){
                val firstNum = when(currentResult){
                    -1.0 -> {
                        oldList[i-1] as Double
                    }
                    else -> currentResult
                }

                val operator = oldList[i]
                val secondNum = oldList[i+1] as Double
                when(operator){
                    'x' ->{
                        currentResult=firstNum * secondNum

                        i++
                        if(i==oldList.size-1){
                            newList.add(currentResult)
                        }
//                        currentIndex = i+1
                    }
                    '/' ->{
                        if (secondNum == 0.0){
                            return arrayListOf<Any>("Error: Divide by Zero")
                        }
                        else {
                            currentResult = firstNum / secondNum
                            i++
                            if(i==oldList.size-1){
                                newList.add(currentResult)
                            }
//                            currentIndex = i+1
                        }
                    }
                    else ->{
                        if(currentResult==-1.0){
                            flag=true
                        }
                        if(flag){
                            newList.add(firstNum)
                            flag=false
                        }else{
                            newList.add(currentResult)
                            currentResult=-1.0
                        }
                        newList.add(operator)
                        if(i+1==oldList.lastIndex&&!(oldList[i+1] is Char)){
                            newList.add(oldList[i+1])
                        }
                    }
                }
            }
//            if (i > currentIndex)
//                newList.add(oldList[i])
            i++
        }
        if(newList.isEmpty()){
            newList.add(currentResult)
        }
        return newList
    }

    private fun sqrtCalculate():String{
        if(resultViewModel.current_resultTV_text.isEmpty()){
            return ""
        }
        var result=resultViewModel.current_resultTV_text.toString().toDouble()
        if(result <0.0){
            return "NaN"
        }
        result=sqrt(result)
        return result.toString()
    }

    private fun breakText() :ArrayList<Any>{
        val list = ArrayList<Any>()
        var num = ""
        for (c in resultViewModel.current_workingTV_text){
            if (c.isDigit() || c == '.')
                num += c
            else {
                list.add(num.toDouble())
                num = ""
                list.add(c)
            }
        }
        if (num.isNotEmpty())
            list.add(num.toDouble())
        return list
    }

    private fun updateWorkingTV(){
        binding.workingTV.text=resultViewModel.current_workingTV_text
    }
    private fun updateResultTV(){
        binding.resultTV.text=resultViewModel.current_resultTV_text
    }
}
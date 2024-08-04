package com.example.firstzaib

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable


class CalculatorViewModel : ViewModel() {
    private val _equationText = MutableLiveData("")
    val equationText: LiveData<String> = _equationText

    private val _resultText = MutableLiveData("0")
    val resultText: LiveData<String> = _resultText

    fun onButtonClick(btn: String) {
        _equationText.value?.let {
            if (btn == "AC") {
                _equationText.value = ""
                _resultText.value = "0"
                return
            }
            if (IsOperator(btn[0])) {
                if (it.isNotEmpty() && IsOperator(it[it.length - 1])) {
                    // Don't append the operator if the last character is already an operator
                    return
                }
            }
            if (btn == "C") {
                _equationText.value = it.substring(0, it.length - 1)
                return
            }
            if (btn == "=") {
                val result = calculateResult(equationText.value.toString())
                _equationText.value = "${it}=$result"
                return
            } else
                _equationText.value = it + btn

            try {

                _resultText.value = calculateResult(equationText.value.toString())
            } catch (_: Exception) {

            }
        }
    }

    fun calculateResult(equation: String): String {
        val context: Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable: Scriptable = context.initStandardObjects()
        var finalResult = context.evaluateString(scriptable, equation, "Javascript", 1, null)
            .toString()
        if (finalResult.endsWith(".0")) {
            finalResult = finalResult.replace(".0", "")
        }
        return finalResult
    }


    private fun IsOperator(character: Char): Boolean {
        return character == '/' || character == '*' || character == '+' || character == '-'
    }
}

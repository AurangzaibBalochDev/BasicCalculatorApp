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
            when (btn) {
                "AC" -> {
                    _equationText.value = ""
                    _resultText.value = "0"
                }
                "C" -> {
                    if (it.isNotEmpty()) {
                        _equationText.value = it.substring(0, it.length - 1)
                    }
                }
                "=" -> {
                    // Only calculate result when "=" is pressed
                    try {
                        val result = calculateResult(_equationText.value.toString())
                        _equationText.value = result
                        _resultText.value = result
                    } catch (e: Exception) {
                        _resultText.value = "Error"
                    }
                }
                else -> {
                    if (IsOperator(btn[0]) && it.isNotEmpty() && IsOperator(it.last())) {
                        // Replace last operator if another operator is clicked
                        _equationText.value = it.substring(0, it.length - 1) + btn
                    } else {
                        _equationText.value = it + btn
                    }

                    // Try calculating the result, but only if the last character isn't an operator
                    if (!IsOperator(btn[0])) {
                        try {
                            _resultText.value = calculateResult(_equationText.value.toString())
                        } catch (e: Exception) {
                            _resultText.value = "Error"
                        }
                    }
                }
            }
        }
    }

    private fun calculateResult(equation: String): String {
        val context: Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable: Scriptable = context.initStandardObjects()
        var finalResult = try {
            context.evaluateString(scriptable, equation, "Javascript", 1, null).toString()
        } catch (e: Exception) {
            "Development in progress"
        }
        if (finalResult.endsWith(".0")) {
            finalResult = finalResult.replace(".0", "")
        }
        return finalResult
    }

    private fun IsOperator(character: Char): Boolean {
        return character == '/' || character == '*' || character == '+' || character == '-' || character == '(' || character == ')'
    }
}

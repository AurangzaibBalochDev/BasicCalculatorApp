package com.example.firstzaib

import android.util.Log
import android.widget.Toast
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

            if (btn == "C") {
                    _equationText.value = it.substring(0, it.length - 1)
                    return
            }
            if (btn == "=") {
                _equationText.value = _resultText.value
                return
            } else
                _equationText.value = it + btn

            try {
                _resultText.value = calculateResult(equationText.value.toString())
            } catch (e: Exception) {

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
}

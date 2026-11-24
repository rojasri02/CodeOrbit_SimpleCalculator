package com.example.pasteldreamcalculator

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pasteldreamcalculator.viewmodel.CalculatorViewModel
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private val viewModel: CalculatorViewModel by viewModels()

    private lateinit var tvExpression: TextView
    private lateinit var tvResult: TextView
    private lateinit var displayLayout: LinearLayout

    private var currentInput = ""
    private var expression = ""
    private var lastResult = ""
    private var isEvaluated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvExpression = findViewById(R.id.tvExpression)
        tvResult = findViewById(R.id.tvResult)
        displayLayout = findViewById(R.id.displayLayout)
        val btnHistory = findViewById<ImageButton>(R.id.btnHistory)

        btnHistory.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        displayLayout.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            val textToCopy = tvResult.text.toString()
            if (textToCopy.isNotEmpty() && textToCopy != "Error") {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Calculator Result", textToCopy)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "Result copied to clipboard!", Toast.LENGTH_SHORT).show()
            }
        }

        if (savedInstanceState != null) {
            currentInput = savedInstanceState.getString("CURRENT_INPUT", "")
            expression = savedInstanceState.getString("EXPRESSION", "")
            lastResult = savedInstanceState.getString("LAST_RESULT", "")
            isEvaluated = savedInstanceState.getBoolean("IS_EVALUATED", false)
            tvExpression.text = expression
            tvResult.text = if (currentInput.isNotEmpty()) currentInput else (if (lastResult.isNotEmpty()) lastResult else "0")
        }

        setupNumberListeners()
        setupOperatorListeners()
        setupSpecialListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("CURRENT_INPUT", currentInput)
        outState.putString("EXPRESSION", expression)
        outState.putString("LAST_RESULT", lastResult)
        outState.putBoolean("IS_EVALUATED", isEvaluated)
    }

    private fun handleButtonPress(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        val anim = AnimationUtils.loadAnimation(this, R.anim.btn_scale)
        view.startAnimation(anim)
    }

    private fun setupNumberListeners() {
        val numberButtons = listOf(
            R.id.btn0 to "0",
            R.id.btn1 to "1",
            R.id.btn2 to "2",
            R.id.btn3 to "3",
            R.id.btn4 to "4",
            R.id.btn5 to "5",
            R.id.btn6 to "6",
            R.id.btn7 to "7",
            R.id.btn8 to "8",
            R.id.btn9 to "9",
            R.id.btnDot to "."
        )

        for ((id, valStr) in numberButtons) {
            val btn = findViewById<Button>(id)
            btn.setOnClickListener { v ->
                handleButtonPress(v)
                if (isEvaluated) {
                    if (valStr == ".") {
                        currentInput = "0."
                    } else {
                        currentInput = valStr
                    }
                    expression = ""
                    isEvaluated = false
                } else {
                    if (valStr == "." && currentInput.contains(".")) return@setOnClickListener
                    if (currentInput == "0" && valStr != ".") {
                        currentInput = valStr
                    } else {
                        currentInput += valStr
                    }
                }
                tvResult.text = currentInput
            }
        }
    }

    private fun setupOperatorListeners() {
        val operators = listOf(
            R.id.btnAdd to "+",
            R.id.btnSub to "-",
            R.id.btnMul to "×",
            R.id.btnDiv to "÷"
        )

        for ((id, op) in operators) {
            val btn = findViewById<Button>(id)
            btn.setOnClickListener { v ->
                handleButtonPress(v)
                if (isEvaluated) {
                    expression = lastResult + " " + op + " "
                    currentInput = ""
                    isEvaluated = false
                } else if (currentInput.isNotEmpty()) {
                    expression += currentInput + " " + op + " "
                    currentInput = ""
                } else if (expression.isNotEmpty() && expression.length >= 3) {
                    expression = expression.trimEnd().dropLast(1) + " " + op + " "
                } else if (op == "-" && currentInput.isEmpty() && expression.isEmpty()) {
                    currentInput = "-"
                }
                tvExpression.text = expression
                tvResult.text = if (currentInput.isNotEmpty()) currentInput else "0"
            }
        }
    }

    private fun setupSpecialListeners() {
        // Clear (AC)
        findViewById<Button>(R.id.btnAc).setOnClickListener { v ->
            handleButtonPress(v)
            currentInput = ""
            expression = ""
            lastResult = ""
            isEvaluated = false
            tvExpression.text = ""
            tvResult.text = "0"
        }

        // Delete / Backspace
        findViewById<Button>(R.id.btnDel).setOnClickListener { v ->
            handleButtonPress(v)
            if (isEvaluated) return@setOnClickListener
            if (currentInput.isNotEmpty()) {
                currentInput = currentInput.dropLast(1)
                tvResult.text = if (currentInput.isEmpty() || currentInput == "-") "0" else currentInput
            }
        }

        // Percentage (%)
        findViewById<Button>(R.id.btnPercent).setOnClickListener { v ->
            handleButtonPress(v)
            if (currentInput.isNotEmpty()) {
                try {
                    val value = currentInput.toDouble() / 100.0
                    currentInput = formatResult(value)
                    tvResult.text = currentInput
                } catch (e: Exception) {
                    tvResult.text = "Error"
                }
            }
        }

        // Equal (=)
        findViewById<Button>(R.id.btnEqual).setOnClickListener { v ->
            handleButtonPress(v)
            if (expression.isNotEmpty() || currentInput.isNotEmpty()) {
                val fullExpr = expression + currentInput
                if (fullExpr.isBlank()) return@setOnClickListener

                try {
                    val result = evaluateExpression(fullExpr)
                    lastResult = formatResult(result)
                    val displayExpr = "$fullExpr ="
                    tvExpression.text = displayExpr
                    tvResult.text = lastResult

                    // Save to Room DB
                    viewModel.saveCalculation(displayExpr, lastResult)

                    currentInput = lastResult
                    expression = ""
                    isEvaluated = true
                } catch (e: Exception) {
                    tvResult.text = "Error"
                    currentInput = ""
                    expression = ""
                    isEvaluated = true
                }
            }
        }
    }

    private fun evaluateExpression(expr: String): Double {
        val tokens = mutableListOf<String>()
        val currentToken = StringBuilder()

        val trimmed = expr.trim()
        var i = 0
        while (i < trimmed.length) {
            val c = trimmed[i]
            if (c == ' ') {
                i++
                continue
            }
            if ((c == '+' || c == '-') && (i == 0 || trimmed[i-1] == '×' || trimmed[i-1] == '÷' || trimmed[i-1] == '+' || trimmed[i-1] == '-')) {
                currentToken.append(c)
                i++
                while (i < trimmed.length && (Character.isDigit(trimmed[i]) || trimmed[i] == '.')) {
                    currentToken.append(trimmed[i])
                    i++
                }
                tokens.add(currentToken.toString())
                currentToken.clear()
            } else if (c == '+' || c == '-' || c == '×' || c == '÷') {
                if (currentToken.isNotEmpty()) {
                    tokens.add(currentToken.toString())
                    currentToken.clear()
                }
                tokens.add(c.toString())
                i++
            } else {
                currentToken.append(c)
                i++
            }
        }
        if (currentToken.isNotEmpty()) {
            tokens.add(currentToken.toString())
        }

        if (tokens.isEmpty()) return 0.0

        // Pass 1: Multiplication and Division
        val intermediate = mutableListOf<String>()
        var idx = 0
        while (idx < tokens.size) {
            val token = tokens[idx]
            if (token == "×" || token == "÷") {
                val prev = intermediate.removeAt(intermediate.size - 1).toDouble()
                val next = tokens[idx + 1].toDouble()
                val res = if (token == "×") {
                    prev * next
                } else {
                    if (next == 0.0) throw ArithmeticException("Division by zero")
                    prev / next
                }
                intermediate.add(res.toString())
                idx += 2
            } else {
                intermediate.add(token)
                idx++
            }
        }

        // Pass 2: Addition and Subtraction
        var result = intermediate[0].toDouble()
        idx = 1
        while (idx < intermediate.size) {
            val op = intermediate[idx]
            val next = intermediate[idx + 1].toDouble()
            if (op == "+") {
                result += next
            } else if (op == "-") {
                result -= next
            }
            idx += 2
        }
        return result
    }

    private fun formatResult(value: Double): String {
        if (value.isNaN() || value.isInfinite()) return "Error"
        return if (value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else {
            val df = DecimalFormat("#.########")
            df.format(value)
        }
    }
}
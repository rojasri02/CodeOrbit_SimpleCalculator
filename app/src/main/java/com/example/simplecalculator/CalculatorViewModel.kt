package com.example.pasteldreamcalculator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteldreamcalculator.data.CalculatorDatabase
import com.example.pasteldreamcalculator.data.HistoryEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {
    private val historyDao = CalculatorDatabase.getDatabase(application).historyDao()

    val historyList: StateFlow<List<HistoryEntity>> = historyDao.getAllHistory()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun saveCalculation(expression: String, result: String) {
        if (expression.isBlank() || result == "Error" || result == "NaN") return
        viewModelScope.launch {
            historyDao.insertHistory(HistoryEntity(expression = expression, result = result))
        }
    }

    fun deleteHistory(id: Long) {
        viewModelScope.launch {
            historyDao.deleteHistoryById(id)
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            historyDao.clearAllHistory()
        }
    }
}
package com.eric.week5.expenses.viewmodel

import androidx.lifecycle.ViewModel
import com.eric.week5.expenses.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ExpenseViewModel : ViewModel() {

    private val _errorMessageIsZero = MutableStateFlow<String?>(null)
    val errorMessageIsZero: StateFlow<String?> = _errorMessageIsZero.asStateFlow()

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
       val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()


    fun addExpense(namaProduk: String, hargaProduk: Int) {
        if (hargaProduk <= 0) {
            _errorMessageIsZero.value = "Amount must be greater than 0"
            return

        }

        val newExpense = Expense(namaProduk, hargaProduk)
        _expenses.update { it + newExpense }
    }

    fun deleteExpense(expense: Expense){
        _expenses.value = _expenses.value - expense
    }

}

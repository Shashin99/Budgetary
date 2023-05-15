package com.example.budgetapp.models

import android.widget.Spinner

data class ExpenseModel(
    var expenseID: String? = null,
    var expenses_cetagory: String? = null,
    var expenses_amount: String? = null,
    var expenses_date: String? = null,
    var expenses_description: String? = null,
)

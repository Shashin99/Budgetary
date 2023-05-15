package com.example.budgetapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.budgetapp.R
import com.example.budgetapp.models.ExpenseModel
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ExpensesHome : AppCompatActivity() {

    private lateinit var btnInsertData: Button
    private lateinit var btnFetchData: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses_home)

        FirebaseApp.initializeApp(this);

        btnInsertData = findViewById(R.id.Add_expense_page_button)
        btnFetchData = findViewById(R.id.View_expense_page_button)

        btnInsertData.setOnClickListener{
            val intent = Intent(this, AddExpense::class.java)
            startActivity(intent)
        }

        btnFetchData.setOnClickListener{
            val intent = Intent(this, FetchingExpense::class.java)
            startActivity(intent)
        }
    }
}
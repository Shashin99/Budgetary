package com.example.budgetapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.budgetapp.R

class HomePage : AppCompatActivity() {

    private lateinit var homeIncome : TextView
    private lateinit var homeBudget : TextView
    private lateinit var homeBill: TextView
    private lateinit var homeExpense: TextView
    private lateinit var homeIcon : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_home)

        homeIncome = findViewById(R.id.homeIncome)
        homeIcon = findViewById(R.id.imageView)

        homeIncome.setOnClickListener{
            val intent = Intent(this, IncomeMain::class.java)
            startActivity(intent)
        }

        homeIcon.setOnClickListener{
            val intent = Intent(this,UserProfile::class.java)
            startActivity(intent)
        }
    }
}
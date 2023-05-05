package com.example.budgetapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.budgetapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var btnViewBill: Button
    private lateinit var btnAddBill: Button

    val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnViewBill = findViewById(R.id.btnViewBill)
        btnAddBill = findViewById(R.id.btnAddBill)

        btnViewBill.setOnClickListener {
            val intent = Intent(this, FetchingBill::class.java)
            startActivity(intent)
        }

        btnAddBill.setOnClickListener {
            val intent = Intent(this, AddBill::class.java)
            startActivity(intent)
        }
    }
}
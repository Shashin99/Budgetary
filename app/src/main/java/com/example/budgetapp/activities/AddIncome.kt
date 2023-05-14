package com.example.budgetapp.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.budgetapp.R
import com.example.budgetapp.models.IncomeModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddIncome : AppCompatActivity() {

    private lateinit var name : EditText
    private lateinit var amount : EditText
    private lateinit var date : EditText
    private lateinit var desc : EditText
    private lateinit var editIncomebtn : Button

    private lateinit var dbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_income_main)

        name = findViewById(R.id.name)
        amount = findViewById(R.id.amount)
        date = findViewById(R.id.date)
        desc = findViewById(R.id.desc)
        editIncomebtn = findViewById(R.id.editIncomebtn)

        dbRef = Firebase.database.reference

        editIncomebtn.setOnClickListener {
            saveIncomeDetails()
        }

    }

    private fun saveIncomeDetails(){
        val incomeName = name.text.toString()
        val incomeAmount = amount.text.toString()
        val incomeDate = date.text.toString()
        val incomeDesc = desc.text.toString()

        if(incomeName.isEmpty()){
            this.name.error= "Please enter the Income Name"
        }
        if(incomeAmount.isEmpty()){
            amount.error= "Please enter the Income amount"
        }
        if(incomeDate.isEmpty()){
            date.error= "Please enter the date"
        }
        if(incomeDesc.isEmpty()){
            desc.error= "Please enter a description"
        }

        val incomeID = dbRef.push().key!!

        val income = IncomeModel(incomeName, incomeAmount,incomeDate, incomeDesc)
        dbRef.child("Adding Income").child(incomeID).setValue(income)
            .addOnCompleteListener{
                Toast.makeText(this,"Income added successfully" ,Toast.LENGTH_SHORT ).show()

                name.text.clear()
                amount.text.clear()
                date.text.clear()
                desc.text.clear()

                val intent = Intent(this,IncomeMain::class.java)
                startActivity(intent)
            }.addOnFailureListener{
                    err->
                Toast.makeText(this,"Error ${err.message}" ,Toast.LENGTH_SHORT ).show()
            }

    }
}
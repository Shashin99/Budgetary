package com.example.budgetapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.budgetapp.R
import com.example.budgetapp.models.ExpenseModel
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class AddExpense : AppCompatActivity() {

    private lateinit var expense_category: Spinner
    private lateinit var amount: EditText
    private lateinit var date_e: EditText
    private lateinit var description: EditText
    private lateinit var btnsave: Button

    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        FirebaseApp.initializeApp(this);

        expense_category = findViewById(R.id.exp_D_category)
        amount = findViewById(R.id.exp_D_expenseamount)
        date_e = findViewById(R.id.exp_D_date)
        description = findViewById(R.id.exp_D_description)
        btnsave = findViewById(R.id.exp_btn_Update)

        dbRef = FirebaseDatabase.getInstance().getReference("Expenses")


        btnsave.setOnClickListener{

           val dateRegex = """^(19|20)\d\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])$""".toRegex()


            if(expense_category.selectedItem == null){
                amount.error = "Please choose  category"
            }

            else if(amount.text.isNullOrEmpty()){
                amount.error = "Please enter amount"
            }

            else if(date_e.text.isNullOrEmpty()){
                date_e.error = "Please enter date"
            }

            else if(description.text.isNullOrEmpty()){
                description.error = "Please enter description"
            }

           else if (!dateRegex.matches(date_e.text.toString())) {
                date_e.error = "Enter a valid date"
            }
            else {
                saveExpenseData()
            }
        }

    }


    private fun saveExpenseData() {
        val expenses_cetagory = expense_category
        val expenses_amount = amount.text.toString()
        val expenses_date = date_e.text.toString()
        val expenses_description = description.text.toString()


        val expenseID = dbRef.push().key!!

        val expense = ExpenseModel(expenseID , expenses_cetagory.selectedItem.toString(), expenses_amount ,  expenses_date , expenses_description)

        dbRef.child(expenseID).setValue(expense)
            .addOnCompleteListener {
                Toast.makeText(this,"Data inserted success fully" , Toast.LENGTH_LONG).show()
                val intent = Intent(this, FetchingExpense::class.java)
                startActivity(intent)
            }.addOnCompleteListener { err->
                Toast.makeText(this, "Error ${err}",Toast.LENGTH_LONG).show()
            }
    }

}
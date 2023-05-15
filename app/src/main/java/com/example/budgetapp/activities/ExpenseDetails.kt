package com.example.budgetapp.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.budgetapp.R
import com.example.budgetapp.models.ExpenseModel
import com.google.firebase.database.FirebaseDatabase

class ExpenseDetails : AppCompatActivity() {

    private lateinit var tvCategory : TextView
    private lateinit var tvamount : TextView
    private lateinit var tvdate : TextView
    private lateinit var tvdescription : TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_details)

        tvCategory = findViewById(R.id.exp_D_category)
        tvamount = findViewById(R.id.exp_D_expenseamount)
        tvdate = findViewById(R.id.exp_D_date)
        tvdescription = findViewById(R.id.exp_D_description)

        tvCategory.text = intent.getStringExtra("expenses_cetagory")
        tvamount.text = intent.getStringExtra("expenses_amount")
        tvdate.text = intent.getStringExtra("expenses_date")
        tvdescription.text = intent.getStringExtra("expenses_description")

        initView()

        btnUpdate = findViewById<Button>(R.id.exp_btn_Update)
        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("expenseID").toString(),
                intent.getStringExtra("expenses_cetagory").toString(),
                intent.getStringExtra("expenses_amount").toString(),
                intent.getStringExtra("expenses_date").toString(),
                intent.getStringExtra("expenses_description").toString()
            )
        }

        btnDelete = findViewById<Button>(R.id.btn_Delete)
        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("expenseID").toString()
            )
        }

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Expenses").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Expense Data deleted" , Toast.LENGTH_LONG).show()
            val intent = Intent(this,FetchingExpense::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this,"Deleting Error ${error.message}" , Toast.LENGTH_LONG).show()
        }
    }

    private fun initView(){}


    private fun openUpdateDialog(
        expenseID:String,
        expenses_cetagory:String,
        expenses_amount : String,
        expenses_date : String ,
        expenses_description : String

    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_exp_update_dialog,null)

        mDialog.setView(mDialogView)

        val etCategory = mDialogView.findViewById<Spinner>(R.id.exp_Update_category)
        val etamount = mDialogView.findViewById<EditText>(R.id.exp_Update_amount)
        val etdate = mDialogView.findViewById<EditText>(R.id.exp_Update_Date)
        val etdescription = mDialogView.findViewById<EditText>(R.id.exp_Update_description)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.exp_btn_fupdate)

        etCategory.setSelection(2)
        etamount.setText(intent.getStringExtra("expenses_amount"))
        etdate.setText(intent.getStringExtra("expenses_date"))
        etdescription.setText(intent.getStringExtra("expenses_description"))

        mDialog.setTitle("Updating Record")
        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateExpenseData(
                expenseID,
                etCategory.selectedItem.toString(),
                etamount.text.toString(),
                etdate.text.toString(),
                etdescription.text.toString()
            )
            Toast.makeText(applicationContext,"Expense data updated" , Toast.LENGTH_LONG).show()

            //setting updated data to text
            tvCategory.text = etCategory.selectedItem.toString()
            tvamount.text = etamount.text.toString()
            tvdate.text = etdate.text.toString()
            tvdescription.text = etdescription.text.toString()

            alertDialog.dismiss()

        }


    }
    private fun updateExpenseData(
        id:String,
        category:String,
        amount : String,
        date :String,
        description : String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Expenses").child(id)
        val expenseInfo = ExpenseModel(id,category,amount,date,description)
        dbRef.setValue(expenseInfo)
    }

}
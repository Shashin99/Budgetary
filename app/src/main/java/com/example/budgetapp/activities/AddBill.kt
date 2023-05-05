package com.example.budgetapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.budgetapp.R
import com.example.budgetapp.models.BillModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddBill : AppCompatActivity() {

    private lateinit var etBillName: Spinner
    private lateinit var etAccName: EditText
    private lateinit var etAccNum: EditText
    private lateinit var etPayAmount: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bill)

        etBillName = findViewById(R.id.etBillName)
        etAccName = findViewById(R.id.etAccName)
        etAccNum = findViewById(R.id.etAccNum)
        etPayAmount = findViewById(R.id.etPayAmount)
        etDescription = findViewById(R.id.etDis)

        btnSaveData = findViewById(R.id.btnNewUpdate)

        dbRef = FirebaseDatabase.getInstance().getReference("Bills")

        btnSaveData.setOnClickListener {
            saveBillData()
        }

    }

    private fun saveBillData() {

        //getting values
        val billName = etBillName
        val billAccName = etAccName.text.toString()
        val billAccNum = etAccNum.text.toString()
        val billPayAmount = etPayAmount.text.toString()
        val billDis = etDescription.text.toString()

        /*if(billName == etBillName.setSelection(0)){
             etBillName.error = "Please Fill this"
         }*/

        if(billAccName.isEmpty()){
            etAccName.error = "Please Fill this"
        }

        if(billAccNum.isEmpty()){
            etAccNum.error = "Please Fill this"
        }

        if(billPayAmount.isEmpty()){
            etPayAmount.error = "Please Fill this"
        }

        if(billDis.isEmpty()){
            etDescription.error = "Please Fill this"
        }

        val billId = dbRef.push().key!!

        val bill = BillModel(billId, billName.selectedItem.toString(), billAccName, billAccNum, billPayAmount,  billDis)

        dbRef.child(billId).setValue(bill)
            .addOnCompleteListener {
                Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_LONG).show()

                etBillName.setSelection(0)
                etAccName.text.clear()
                etAccNum.text.clear()
                etPayAmount.text.clear()
                etDescription.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}
package com.example.budgetapp.activities


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.budgetapp.R
import com.example.budgetapp.databinding.EditIncomeMainBinding
import com.example.budgetapp.models.IncomeModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditIncome : AppCompatActivity() {
    private lateinit var binding: EditIncomeMainBinding
    private lateinit var eName: TextView
    private lateinit var eAmount: TextView
    private lateinit var edate: TextView
    private lateinit var edesc: TextView
    private lateinit var editbtn: Button
    private lateinit var deletebtn: Button

    private lateinit var dbRef : DatabaseReference

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditIncomeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eName = findViewById(R.id.name)
        eAmount = findViewById(R.id.amount)
        edate = findViewById(R.id.date)
        edesc = findViewById(R.id.desc)
        editbtn = findViewById(R.id.editIncomebtn)
        deletebtn = findViewById(R.id.i_delete_details)

        eName.text = intent.getStringExtra("incomeName")
        eAmount.text = intent.getStringExtra("incomeAmount")
        edate.text = intent.getStringExtra("incomeDate")
        edesc.text = intent.getStringExtra("incomeDesc")

        editbtn.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("incomeName").toString(),
                intent.getStringExtra("incomeAmount").toString()

            )
        }
        deletebtn.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("incomeName").toString(),
            )
        }


        initView()
        setValuesToView()
    }

    private fun deleteRecord(
        incomeID:String
    ){
        dbRef = FirebaseDatabase.getInstance().getReference("Adding Income").child(incomeID)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Income Record deleted" , Toast.LENGTH_SHORT).show()
            val intent = Intent(this , IncomeMain::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{
                error->
            Toast.makeText(this,"Error ${error.message}" , Toast.LENGTH_SHORT).show()
        }
    }
    private fun initView() {}

    private fun setValuesToView() {}

    private fun openUpdateDialog(
        incomeID: String,
        incomeName: String,

        ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialogbox, null)

        mDialog.setView(mDialogView)

        val upIncome = mDialogView.findViewById<EditText>(R.id.upIncome)
        val upAmount = mDialogView.findViewById<EditText>(R.id.upAmount)
        val upDate = mDialogView.findViewById<EditText>(R.id.upDate)
        val upDesc = mDialogView.findViewById<EditText>(R.id.upDesc)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        upIncome.setText(intent.getStringExtra("incomeName").toString())
        upAmount.setText(intent.getStringExtra("incomeAmount").toString())
        upDate.setText(intent.getStringExtra("incomeDate").toString())
        upDesc.setText(intent.getStringExtra("incomeDesc").toString())

        mDialog.setTitle("Updating $incomeName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateIncomeData(
                incomeID,
                upIncome.text.toString(),
                upAmount.text.toString(),
                upDate.text.toString(),
                upDesc.text.toString()
            )
            Toast.makeText(
                applicationContext,
                "Income data Updated Successfully",
                Toast.LENGTH_SHORT
            ).show()

            eName.text = upIncome.text.toString()
            eAmount.text = upAmount.text.toString()
            edate.text = upDate.text.toString()
            edesc.text = upDesc.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateIncomeData(
        incomeID: String,
        incomeName: String,
        incomeAmount: String,
        incomeDate: String,
        incomeDesc: String
    ) {
        dbRef = FirebaseDatabase.getInstance().getReference("Adding Income").child(incomeID)
        val incomeInfo = IncomeModel(incomeName, incomeAmount, incomeDate, incomeDesc)
        dbRef.setValue(incomeInfo)

    }
}

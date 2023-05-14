package com.example.budgetapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.budgetapp.R
import com.example.budgetapp.models.BillModel
import com.google.firebase.database.FirebaseDatabase

class BillDetails : AppCompatActivity() {

    private lateinit var tvBillId: TextView
    private lateinit var tvBillName: TextView
    private lateinit var tvAccName: TextView
    private lateinit var tvAccNum: TextView
    private lateinit var tvPayAmount: TextView
    private lateinit var tvDis: TextView

    private lateinit var btnNewUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnProceed: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_details)

        initView()
        setValuesToViews()

        btnProceed.setOnClickListener {
            val intent = Intent(this, Payment::class.java)
            startActivity(intent)
        }

        btnNewUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("billId").toString(),
                intent.getStringExtra("billName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("billId").toString()
            )
        }

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Bills").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Bill data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingBill::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this,"Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        tvBillId = findViewById(R.id.tvBillId)
        tvBillName = findViewById(R.id.tvBillName)
        tvAccName = findViewById(R.id.tvAccName)
        tvAccNum = findViewById(R.id.tvAccNum)
        tvPayAmount = findViewById(R.id.tvPayAmount)
        tvDis = findViewById(R.id.tvDis)

        btnNewUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        btnProceed = findViewById(R.id.btnProceed)
    }

    private fun setValuesToViews() {
        tvBillId.text = intent.getStringExtra("billId")
        tvBillName.text = intent.getStringExtra("billName")
        tvAccName.text = intent.getStringExtra("billAccName")
        tvAccNum.text = intent.getStringExtra("billAccNum")
        tvPayAmount.text = intent.getStringExtra("billPayAmount")
        tvDis.text = intent.getStringExtra("billDis")

    }

    private fun openUpdateDialog(
        billId: String,
        billName: String
    ){

        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater

        val mDialogView = inflater.inflate(R.layout.activity_update_dialog, null)

        mDialog.setView(mDialogView)

        val etBillName = mDialogView.findViewById<Spinner>(R.id.etBillName)
        val etAccName = mDialogView.findViewById<EditText>(R.id.etAccName)
        val etAccNum = mDialogView.findViewById<EditText>(R.id.etAccNum)
        val etPayAmount = mDialogView.findViewById<EditText>(R.id.etPayAmount)
        val etDis = mDialogView.findViewById<EditText>(R.id.etDis)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnNewUpdate)

        etBillName.setSelection(1)
        etAccName.setText(intent.getStringExtra("billAccName").toString())
        etAccNum.setText(intent.getStringExtra("billAccNum").toString())
        etPayAmount.setText(intent.getStringExtra("billPayAmount").toString())
        etDis.setText(intent.getStringExtra("billDis").toString())

        mDialog.setTitle("Updating $billName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateBillData(
                billId,
                etBillName.selectedItem.toString(),
                etAccName.text.toString(),
                etAccNum.text.toString(),
                etPayAmount.text.toString(),
                etDis.text.toString(),
            )

            Toast.makeText(applicationContext, "Bill Data Updated Successfully", Toast.LENGTH_LONG).show()

            //setting updated data to our textViews
            tvBillName.text = etBillName.selectedItem.toString()
            tvAccName.text = etAccName.text.toString()
            tvAccNum.text = etAccNum.text.toString()
            tvPayAmount.text = etPayAmount.text.toString()
            tvDis.text = etDis.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateBillData(
        id:String,
        name:String,
        accName:String,
        accNum:String,
        payAmount:String,
        dis:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Bills").child(id)
        val billInfo = BillModel(id, name, accName, accNum, payAmount, dis)
        dbRef.setValue(billInfo)
    }
}
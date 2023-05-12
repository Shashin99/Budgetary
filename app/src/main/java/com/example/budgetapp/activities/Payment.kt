package com.example.budgetapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.budgetapp.R
import com.example.budgetapp.models.FormData
import com.example.budgetapp.models.validations.ValidationResult

class Payment : AppCompatActivity() {

    private lateinit var notificationHelper: NotificationHelper

    lateinit var etHolderName: EditText
    lateinit var etCardNumber: EditText
    lateinit var tvYear: TextView
    lateinit var spnYear: Spinner
    lateinit var tvMonth: TextView
    lateinit var spnMonth: Spinner
    lateinit var etCvc: EditText
    lateinit var cbAgree: CheckBox
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        etHolderName = findViewById(R.id.etHolderName)
        etCardNumber = findViewById(R.id.etCardNumber)
        tvYear = findViewById(R.id.tvYear)
        spnYear = findViewById(R.id.spnYear)
        tvMonth = findViewById(R.id.tvMonth)
        spnMonth = findViewById(R.id.spnMonth)
        etCvc = findViewById(R.id.etCvc)
        cbAgree = findViewById(R.id.cbAgreement)

        notificationHelper = NotificationHelper(this)
    }

    private fun displayAlert(title:String, message:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, which ->
            // Do something when the "OK" button is clicked
            notificationHelper.pay()
        }
        val dialog = builder.create()
        dialog.show()
    }

    //pay function (validation)
    fun pay(v: View){
        val myForm = FormData(
            etHolderName.text.toString(),
            etCardNumber.text.toString(),
            spnYear.selectedItem.toString(),
            spnMonth.selectedItem.toString(),
            etCvc.text.toString(),
            cbAgree.isChecked
        )

        val holderNameValidation = myForm.validateHolderName()
        val cardNumberValidation = myForm.validateCardNumber()
        val spnYearValidation = myForm.validateYear()
        val spnMonthValidation = myForm.validateMonth()
        val cvcValidation = myForm.validateCvc()
        val cbAgreeValidation = myForm.validateAgreement()

        when(holderNameValidation){
            is ValidationResult.Valid ->{
                count ++
            }
            is ValidationResult.Invalid ->{
                etHolderName.error = holderNameValidation.errorMessage
            }
            is ValidationResult.Empty ->{
                etHolderName.error = holderNameValidation.errorMessage
            }
        }

        when(cardNumberValidation){
            is ValidationResult.Valid ->{
                count ++
            }
            is ValidationResult.Invalid ->{
                etCardNumber.error = cardNumberValidation.errorMessage
            }
            is ValidationResult.Empty ->{
                etCardNumber.error = cardNumberValidation.errorMessage
            }
        }

        when(spnYearValidation){
            is ValidationResult.Valid ->{
                count ++
            }
            is ValidationResult.Invalid ->{
                val tv:TextView = spnYear.selectedView as TextView
                tv.error =""
                tv.text = spnYearValidation.errorMessage
            }
            is ValidationResult.Empty ->{
                val tv:TextView = spnYear.selectedView as TextView
                tv.error =""
                tv.text = spnYearValidation.errorMessage
            }
        }

        when(spnMonthValidation){
            is ValidationResult.Valid ->{
                count ++
            }
            is ValidationResult.Invalid ->{
                val tv:TextView = spnMonth.selectedView as TextView
                tv.error =""
                tv.text = spnMonthValidation.errorMessage
            }
            is ValidationResult.Empty ->{
                val tv:TextView = spnMonth.selectedView as TextView
                tv.error =""
                tv.text = spnMonthValidation.errorMessage
            }
        }

        when(cvcValidation){
            is ValidationResult.Valid ->{
                count ++
            }
            is ValidationResult.Invalid ->{
                etCvc.error = cvcValidation.errorMessage
            }
            is ValidationResult.Empty ->{
                etCvc.error = cvcValidation.errorMessage
            }
        }

        when(cbAgreeValidation){
            is ValidationResult.Valid ->{
                count ++
            }
            is ValidationResult.Invalid ->{
                displayAlert("Error", cbAgreeValidation.errorMessage)
            }
            is ValidationResult.Empty ->{
            }
        }
        if(count==6){
            displayAlert("Success","Payment Done Successfully")
        }


    }

    //cancel function
    fun cancel(v: View){
        etHolderName.setText("")
        etCardNumber.setText("")
        spnYear.setSelection(0)
        spnMonth.setSelection(0)
        etCvc.setText("")
        cbAgree.isChecked = false
        count =0
    }
}

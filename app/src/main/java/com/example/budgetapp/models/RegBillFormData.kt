package com.example.budgetapp.models

//import com.example.budgetapp.models.validations.ValidationResult
//
//class RegBillFormData(
//
//    // registering bill
//    private var accountName: String,
//    private var accountNumber: String,
//    private var amountPay: String,
//    private var description: String
//
//){
//
//    fun validateAccountName(): ValidationResult {
//        return if(accountName.isEmpty()){
//            ValidationResult.Empty("Enter Account Name")
//        } else{
//            ValidationResult.Valid
//        }
//    }
//
//    fun validateAccountNumber(): ValidationResult {
//        return if(accountNumber.isEmpty()){
//            ValidationResult.Empty("Enter Account Number")
//        } else{
//            ValidationResult.Valid
//        }
//    }
//
//    fun validateAmountToBePay(): ValidationResult {
//        return if(amountPay.isEmpty()){
//            ValidationResult.Empty("Enter Amount")
//        } else{
//            ValidationResult.Valid
//        }
//    }
//
//    fun validateDescription(): ValidationResult {
//        return if(description.isEmpty()){
//            ValidationResult.Empty("Enter Description")
//        } else{
//            ValidationResult.Valid
//        }
//    }
//
//}
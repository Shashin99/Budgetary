package com.example.budgetapp.models

import com.example.budgetapp.models.validations.ValidationResult

class FormData (

    // payment form
    private var holderName:String,
    private var cardNumber:String,
    private var year:String,
    private var month:String,
    private var cvc:String,
    private var agree:Boolean

) {

    fun validateHolderName():ValidationResult {
        return if(holderName.isEmpty()){
            ValidationResult.Empty("Enter  HolderName Correctly")
        } else{
            ValidationResult.Valid
        }
    }

    fun validateCardNumber():ValidationResult{
        return if(cardNumber.isEmpty()){
            ValidationResult.Empty("Enter Your CardNumber")
        }else if(cardNumber.length<16){
            ValidationResult.Invalid("Invalid Card Number")
        }else if(cardNumber.length>16) {
            ValidationResult.Invalid("Invalid Card Number")
        }else{
            ValidationResult.Valid
        }
    }

    fun validateYear(): ValidationResult {
        return if (year.isEmpty()) {
            ValidationResult.Empty("Year is empty")
        } else {
            ValidationResult.Valid
        }

    }

    fun validateMonth(): ValidationResult {
        return if (month.isEmpty()) {
            ValidationResult.Empty("Month is empty")
        } else {
            ValidationResult.Valid
        }
    }

    fun validateCvc():ValidationResult{
        return if(cvc.isEmpty()){
            ValidationResult.Empty("Required")
        }else if(cvc.length<3){
            ValidationResult.Invalid("Invalid")
        }else if(cvc.length>3) {
            ValidationResult.Invalid("Invalid")
        }else{
            ValidationResult.Valid
        }
    }

    fun validateAgreement():ValidationResult{
        return if(!agree){
            ValidationResult.Invalid("You must agree for the terms and conditions")
        }else{
            ValidationResult.Valid
        }
    }

}
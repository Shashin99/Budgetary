package com.example.planmanegment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.planmanegment.Model.PlanModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddPlan : AppCompatActivity() {

    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plan)



        var submit=findViewById<Button>(R.id.submit)



        submit.setOnClickListener {
            addData()
        }


    }

    fun addData(){
        var category=findViewById<EditText>(R.id.categoryinput)
        var amount=findViewById<EditText>(R.id.amountinput)
        var description=findViewById<EditText>(R.id.descriptioninput)

        val Category=category.text.toString()
        val Amount=amount.text.toString()
        val Description=description.text.toString()
        val email="user1"

        if(Category.isNotEmpty() && Amount.isNotEmpty() && Description.isNotEmpty() && email.isNotEmpty()){

            dbRef=FirebaseDatabase.getInstance().getReference("Plans").child(email)

            val planId=dbRef.push().key!!
            val planData= PlanModel(planId,Description,Amount,Category,email)

            dbRef.child(planId).setValue(planData).addOnSuccessListener {

                val intent=Intent(this,ShowAllData::class.java)
                intent.putExtra("email",email)
                startActivity(intent)

                Toast.makeText(this,"Data added",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this,"Data Not Added",Toast.LENGTH_LONG).show()
            }
        }
        else{
            if (Category.isEmpty()){
                Toast.makeText(this,"please fill Category",Toast.LENGTH_LONG).show()
            }
            if (Amount.isEmpty()){
                Toast.makeText(this,"please fill Amount",Toast.LENGTH_LONG).show()
            }
            if(Description.isEmpty()){
                Toast.makeText(this,"please fill Desription",Toast.LENGTH_LONG).show()
            }
            if(email.isEmpty()){
                Toast.makeText(this,"please fill Email",Toast.LENGTH_LONG).show()
            }
        }



    }


}
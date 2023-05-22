package com.example.planmanegment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateDelete : AppCompatActivity() {

    private lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)

        val planId=intent.getStringExtra("id")
        database = FirebaseDatabase.getInstance().reference.child("Plans").child("user1").child(planId!!)

        val PLanCategory=findViewById<EditText>(R.id.categoryEdite)
        val PLanamount=findViewById<EditText>(R.id.amountEdite)
        val delete=findViewById<Button>(R.id.delete)
        val update=findViewById<Button>(R.id.update)

        var planAmount=intent.getStringExtra("planAmount")
        var planCategory=intent.getStringExtra("planCategory")
        /*val planId=intent.getStringExtra("id")*/

        PLanCategory.setText(planCategory)
        PLanamount.setText(planAmount)

        update.setOnClickListener {

            updateDetail(PLanCategory.text.toString(),PLanamount.text.toString())
        }

        delete.setOnClickListener {
            deleteData()
        }

    }

    fun updateDetail(planCategory: String, planAmount: String) {
        val updates = mapOf(
            "planCategory" to planCategory,
            "planAmount" to planAmount
        )

        database.updateChildren(updates)
            .addOnSuccessListener {
                Toast.makeText(this, "Details updated successfully", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, ShowAllData::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Details update unsuccessful", Toast.LENGTH_LONG).show()
            }
    }

    fun deleteData(){
        var id = intent.getStringExtra("id").toString()

        database.removeValue().addOnSuccessListener {
            Toast.makeText(this,"delete details Success", Toast.LENGTH_LONG).show()
            intent = Intent(applicationContext, ShowAllData::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(this,"delete details Unsuccess", Toast.LENGTH_LONG).show()

        }
    }
}
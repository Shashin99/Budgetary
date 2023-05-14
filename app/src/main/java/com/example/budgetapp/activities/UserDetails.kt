package com.example.budgetapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.budgetapp.R
import com.example.budgetapp.models.UserModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserDetails : AppCompatActivity() {

    private lateinit var editTextPhone: EditText
    private lateinit var textView5: EditText
    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var btnDetails: Button

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        editTextPhone = findViewById(R.id.editTextPhone)
        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        textView5 = findViewById(R.id.textView5)
        btnDetails = findViewById(R.id.btnDetails)

        dbRef = Firebase.database.reference

        btnDetails.setOnClickListener {
            try {
                saveUserDetails()
            } catch (e: Exception) {
                Log.e("TAG", "Exception: ${e.message}")
            }
        }


    }

    private fun saveUserDetails() {
        val userTel = editTextPhone.text.toString()
        val userGoals = textView5.text.toString()
        val userEmail = editEmail.text.toString()
        val userName = editName.text.toString()


        val user = UserModel(userName,userEmail,userTel, userGoals)
        dbRef.child("SetUserDetails").child(userName).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(this, "User Details saved", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }

    }
}

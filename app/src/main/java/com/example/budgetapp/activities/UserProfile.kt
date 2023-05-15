package com.example.budgetapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.budgetapp.databinding.ActivityUserProfileBinding
import com.google.firebase.database.*


class UserProfile : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("SetUserDetails")

        binding.udimageView3.setOnClickListener {
            val userName:String = binding.udtextView2.text.toString()
            if(userName.isNotEmpty()){
                readData(userName)
            }else{
                Toast.makeText(this,"Enter Your User Name to Continue", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun readData(userName:String){

        dbRef?.child(userName)?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userEmail = dataSnapshot.child("userEmail").getValue(String::class.java)
                    val userTel = dataSnapshot.child("userTel").getValue(String::class.java)
                    val userGoal = dataSnapshot.child("userGoals").getValue(String::class.java)

                    binding.udtextEmail.text = userEmail
                    binding.udtextTel.text = userTel
                    binding.udtextView3.text = userGoal
                }
                else {
                    Toast.makeText(applicationContext, "User doesn't exist", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle cancelled event
            }
        })
    }

}


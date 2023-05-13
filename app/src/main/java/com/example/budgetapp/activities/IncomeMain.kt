package com.example.budgetapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetapp.adapters.IncomeAdapter
import com.example.budgetapp.databinding.ActivityIncomeMainBinding
import com.example.budgetapp.models.IncomeModel
import com.google.firebase.database.*


class IncomeMain : AppCompatActivity() {

    private lateinit var binding: ActivityIncomeMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var incomeAdapter: IncomeAdapter
    private lateinit var incomeList: ArrayList<IncomeModel>

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityIncomeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("Adding Income")
        recyclerView = findViewById(R.id.IncomeRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        incomeList = ArrayList()
        incomeAdapter = IncomeAdapter(incomeList)
        recyclerView.adapter = incomeAdapter
        getIncomeData()

        binding.addIncomebtn.setOnClickListener {
            val intent = Intent(this, AddIncome::class.java)
            startActivity(intent)
        }
    }

    private fun getIncomeData() {


        dbRef = FirebaseDatabase.getInstance().getReference("Adding Income")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                incomeList.clear()
                var totalAmount = 0
                if (snapshot.exists()) {
                    for (incomeSnap in snapshot.children) {
                        val inData = incomeSnap.getValue(IncomeModel::class.java)
                        incomeList.add(inData!!)
                        totalAmount += inData.incomeAmount?.toIntOrNull() ?: 0



                    }

                    //display the total amount
                    binding.totalIncome.text = "$totalAmount"
                    //add total amount to Firebase
                    val totalRef = FirebaseDatabase.getInstance().getReference("Total Income")
                    totalRef.setValue(totalAmount)

                    incomeAdapter.notifyDataSetChanged()
                    recyclerView.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        incomeAdapter.setOnItemClickListener(object : IncomeAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@IncomeMain, EditIncome::class.java)
                //intent.putExtra("incomeID" , incomeList[position].incomeID)
                intent.putExtra("incomeName", incomeList[position].incomeName)
                intent.putExtra("incomeAmount", incomeList[position].incomeAmount)
                intent.putExtra("incomeDate", incomeList[position].incomeDate)
                intent.putExtra("incomeDesc", incomeList[position].incomeDesc)
                startActivity(intent)
            }


        })
    }
}



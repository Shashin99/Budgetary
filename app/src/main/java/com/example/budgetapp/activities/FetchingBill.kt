package com.example.budgetapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetapp.R
import com.example.budgetapp.adapters.BillAdapter
import com.example.budgetapp.models.BillModel
import com.google.firebase.database.*

class FetchingBill : AppCompatActivity() {

    private lateinit var billRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView

    private lateinit var billList: ArrayList<BillModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching_bill)

        //fetching bill data in a recycler view
        billRecyclerView = findViewById(R.id.rvBill)
        billRecyclerView.layoutManager = LinearLayoutManager(this)
        billRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        billList = arrayListOf<BillModel>()

        getBillsData()
    }

    private fun getBillsData() {
        billRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Bills")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                billList.clear()
                if(snapshot.exists()){
                    for (billSnap in snapshot.children){
                        val billData = billSnap.getValue(BillModel::class.java)
                        billList.add(billData!!)
                    }
                    val mAdapter = BillAdapter(billList)
                    billRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object:BillAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingBill, BillDetails::class.java)

                            //pull extras
                            intent.putExtra("billId",billList[position].billId)
                            intent.putExtra("billName",billList[position].billName)
                            intent.putExtra("billAccName",billList[position].billAccName)
                            intent.putExtra("billAccNum",billList[position].billAccNum)
                            intent.putExtra("billPayAmount",billList[position].billPayAmount)
                            intent.putExtra("billDis",billList[position].billDis)
                            startActivity(intent)
                        }
                    })

                    billRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
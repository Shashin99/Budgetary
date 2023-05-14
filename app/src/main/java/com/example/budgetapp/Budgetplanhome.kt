package com.example.planmanegment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.planmanegment.Adapter.PlanAdapter
import com.example.planmanegment.Model.PlanModel
import com.google.firebase.database.*

class ShowAllData : AppCompatActivity() {

    private lateinit var employeeItem: RecyclerView
    private lateinit var empList:ArrayList<PlanModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_to_re)

        employeeItem=findViewById(R.id.rv)

        employeeItem.layoutManager= LinearLayoutManager(this,)
        employeeItem.setHasFixedSize(true)
        empList= arrayListOf<PlanModel>()

        getEmployeeData()

        var Addnewplan=findViewById<Button>(R.id.btnaddnewplan)

        Addnewplan.setOnClickListener {
            val intent=Intent(this,AddPlan::class.java)
            startActivity(intent)
        }

    }

    private fun getEmployeeData(){
        employeeItem.visibility= View.GONE

        val LoginEmail=intent.getStringExtra("email")

        dbRef= FirebaseDatabase.getInstance().getReference("Plans").child("user1")

        dbRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if(snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData=empSnap.getValue(PlanModel::class.java)
                        empList.add(empData!!)
                    }

                    val mAdapter= PlanAdapter(empList)

                    mAdapter.setOnItemClickListener(object:PlanAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent= Intent(this@ShowAllData,UpdateDelete::class.java)
                            //put extras
                            intent.putExtra("planAmount",empList[position].planAmount)
                            intent.putExtra("planCategory",empList[position].planCategory)
                            intent.putExtra("id",empList[position].planId)
                            startActivity(intent)
                        }
                    })

                    employeeItem.adapter=mAdapter
                    employeeItem.visibility= View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }

        })
    }
}
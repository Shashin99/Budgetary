package com.example.budgetapp.activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetapp.R
import com.example.budgetapp.adapters.Expensesadapter
import com.example.budgetapp.models.ExpenseModel
import com.google.firebase.database.*

class FetchingExpense : AppCompatActivity() {

    private lateinit var expenseRecyclerView: RecyclerView
    private lateinit var tvLoadingDat: TextView
    private lateinit var expense_List: ArrayList<ExpenseModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching_expense)

        expenseRecyclerView = findViewById(R.id.rvExpenseseList)
        expenseRecyclerView.layoutManager = LinearLayoutManager(this)
        expenseRecyclerView.setHasFixedSize(true)
        tvLoadingDat = findViewById(R.id.tvLoading)

        expense_List = arrayListOf<ExpenseModel>()

        getEmployeeData()
    }

    private fun searchExpenses(searchQuery: String) {
        dbRef = FirebaseDatabase.getInstance().getReference("Expenses")
        val query = dbRef.orderByChild("expenses_cetagory").equalTo(searchQuery)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                expense_List.clear()
                if (snapshot.exists()) {
                    for (expenseSnap in snapshot.children) {
                        val expenseData = expenseSnap.getValue(ExpenseModel::class.java)
                        expense_List.add(expenseData!!)
                    }
                    val mAdapter = Expensesadapter(expense_List)
                    expenseRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : Expensesadapter.onItemClickListner {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingExpense, ExpenseDetails::class.java)

                            //put extras

                            intent.putExtra("expenseID", expense_List[position].expenseID)
                            intent.putExtra("expenses_cetagory", expense_List[position].expenses_cetagory)
                            intent.putExtra("expenses_amount", expense_List[position].expenses_amount)
                            intent.putExtra("expenses_date", expense_List[position].expenses_date)
                            intent.putExtra("expenses_description", expense_List[position].expenses_description)
                            startActivity(intent)
                        }
                    })

                    expenseRecyclerView.visibility = View.VISIBLE
                    tvLoadingDat.visibility = View.GONE
                } else {
                    // No matching data found
                    expense_List.clear()
                    val mAdapter = Expensesadapter(expense_List)
                    expenseRecyclerView.adapter = mAdapter
                    expenseRecyclerView.visibility = View.VISIBLE
                    tvLoadingDat.visibility = View.GONE
                    Toast.makeText(this@FetchingExpense, "No matching data found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to search expenses: ${error.message}")
                Toast.makeText(this@FetchingExpense, "Failed to search expenses: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun getEmployeeData(){
        expenseRecyclerView.visibility = View.GONE
        tvLoadingDat.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Expenses")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                expense_List.clear()
                if (snapshot.exists()){
                    for (expenseSnap in snapshot.children){
                        val ExpenseData = expenseSnap.getValue(ExpenseModel::class.java)
                        expense_List.add(ExpenseData!!)
                    }
                    val mAdapter = Expensesadapter(expense_List)
                    expenseRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : Expensesadapter.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingExpense , ExpenseDetails::class.java)

                            //put extras

                            intent.putExtra("expenseID",expense_List[position].expenseID)
                            intent.putExtra("expenses_cetagory",expense_List[position].expenses_cetagory)
                            intent.putExtra("expenses_amount",expense_List[position].expenses_amount)
                            intent.putExtra("expenses_date",expense_List[position].expenses_date)
                            intent.putExtra("expenses_description",expense_List[position].expenses_description)
                            startActivity(intent)
                        }

                    })

                    expenseRecyclerView.visibility = View.VISIBLE
                    tvLoadingDat.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}
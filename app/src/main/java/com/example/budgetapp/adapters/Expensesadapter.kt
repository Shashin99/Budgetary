package com.example.budgetapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetapp.R
import com.example.budgetapp.models.ExpenseModel


class Expensesadapter(private val expense_list: ArrayList<ExpenseModel>) :
    RecyclerView.Adapter<Expensesadapter.ViewHolder>() {

    private lateinit var mListner: onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListner: onItemClickListner){
        mListner = clickListner
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Expensesadapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.expenses_item , parent ,false)
        return ViewHolder(itemView , mListner)

    }

    override fun onBindViewHolder(holder: Expensesadapter.ViewHolder, position: Int) {
        val cuurentExpense = expense_list[position]
        holder.tvExpense.text = cuurentExpense.expenses_cetagory
        holder.tvReason.text = cuurentExpense.expenses_description

    }

    override fun getItemCount(): Int {
        return expense_list.size
    }

    class ViewHolder(itemView: View , clickListner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {

        val tvExpense : TextView = itemView.findViewById(R.id.expenseTodo)
        val tvReason : TextView = itemView.findViewById(R.id.tvDescription)
        init {
            itemView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
        }


    }


}
package com.example.budgetapp.adapters


import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetapp.R
import com.example.budgetapp.models.IncomeModel


class IncomeAdapter( private val incomeList: ArrayList<IncomeModel>) :
    RecyclerView.Adapter<IncomeAdapter.ViewHolder>(){

    private lateinit var mListener: OnItemClickListener

    interface  OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener){
        mListener = clickListener
    }
    init {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list,parent,false)
        return ViewHolder(itemView , mListener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentIncome = incomeList[position]
        holder.listName.text = currentIncome.incomeName
        //holder.listAmount.text = currentIncome.incomeAmount
    }

    override fun getItemCount(): Int {
        return incomeList.size
    }

    class ViewHolder(itemView: View , clickListener: OnItemClickListener):RecyclerView.ViewHolder(itemView) {
        val listName : TextView = itemView.findViewById(R.id.listName)
        //val listAmount: TextView = itemView.findViewById(R.id.listAmount)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}



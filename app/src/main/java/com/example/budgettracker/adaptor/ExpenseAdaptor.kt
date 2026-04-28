package com.example.budgettracker.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgettracker.R
import com.example.budgettracker.data.Expense

class ExpenseAdaptor(private var expenses: List<Expense>) :
    RecyclerView.Adapter<ExpenseAdaptor.ExpenseViewHolder>() {

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtExpense: TextView = itemView.findViewById(R.id.txtExpense)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_expense, parent, false)

        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.txtExpense.text =
            "💰 R${expense.amount}\n" +
                    "📂 ${expense.category}\n" +
                    "📅 ${expense.date}\n" +
                    "⏰ ${expense.startTime} - ${expense.endTime}\n" +
                    "📝 ${expense.description}"
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    fun updateData(newExpenses: List<Expense>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }
}
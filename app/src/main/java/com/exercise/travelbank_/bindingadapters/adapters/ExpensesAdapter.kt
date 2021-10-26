package com.exercise.travelbank_.bindingadapters.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.exercise.travelbank_.databinding.ItemExpensesRowBinding
import com.exercise.travelbank_.models.ExpensesDTO
import com.exercise.travelbank_.util.ExpensesDiffUtil


class ExpensesAdapter : RecyclerView.Adapter<ExpensesAdapter.ExpensesHolder>() {

    private var currentExpense = emptyList<ExpensesDTO>()
  class ExpensesHolder(private val binding: ItemExpensesRowBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(expensesDTO: ExpensesDTO){
            binding.expenses = expensesDTO
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent:ViewGroup): ExpensesHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemExpensesRowBinding.inflate(layoutInflater,parent,false)
                return ExpensesHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesHolder {
        return ExpensesHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ExpensesHolder, position: Int) {
       val currentExpense = currentExpense[position]
        holder.bind(currentExpense)
    }

    override fun getItemCount(): Int {
        return currentExpense.size
    }

    fun setData(newData: List<ExpensesDTO>){
        val expensesDiffUtil = ExpensesDiffUtil(currentExpense, newData)
        val diffUtilResult = DiffUtil.calculateDiff(expensesDiffUtil)

        currentExpense = newData

        diffUtilResult.dispatchUpdatesTo(this)
    }
}
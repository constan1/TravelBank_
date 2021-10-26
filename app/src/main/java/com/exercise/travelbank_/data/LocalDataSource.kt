package com.exercise.travelbank_.data

import com.exercise.travelbank_.data.database.ExpensesDAO
import com.exercise.travelbank_.data.database.ExpensesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val expensesDAO: ExpensesDAO
) {
    fun getAllExpenses(): Flow<List<ExpensesEntity>>{
        return expensesDAO.getAllExpenses()
    }
   suspend fun cacheExpenses(expensesEntity: ExpensesEntity){
        expensesDAO.cacheExpenses(expensesEntity)
    }
}
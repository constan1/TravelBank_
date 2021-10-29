package com.exercise.travelbank_.data.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.exercise.travelbank_.data.network.ExpensesApi
import com.exercise.travelbank_.models.ExpensesDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class FakeRepositoryTest : ExpensesDAO {


    private val expenses = mutableListOf<ExpensesEntity>()

    private val getExpenses = MutableLiveData<List<ExpensesEntity>>(expenses)


    override fun getAllExpenses(): Flow<List<ExpensesEntity>> {
        return getExpenses.asFlow()
    }

    override suspend fun cacheExpenses(expensesEntity: ExpensesEntity) {
        expenses.add(expensesEntity)
    }


}
package com.exercise.travelbank_.data.datasources

import com.exercise.travelbank_.data.network.ExpensesApi
import com.exercise.travelbank_.models.ExpensesDTO
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val ExpensesApi: ExpensesApi
) {

    suspend fun getExpenses(): Response<List<ExpensesDTO>> {
        return ExpensesApi.getExpenses()
    }
}
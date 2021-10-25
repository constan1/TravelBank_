package com.exercise.travelbank_.data.network

import com.exercise.travelbank_.data.ExpensesResponse
import retrofit2.http.GET

interface ExpensesApi {


    companion object {
        const val BASE_URL = "https://run.mocky.io/v3/"
    }

    @GET("178cbbee-c634-4a51-afb8-dcd75c190d29")
    suspend fun getExpenses(): ExpensesResponse

}
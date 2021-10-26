package com.exercise.travelbank_.data.network

import com.exercise.travelbank_.models.ExpensesDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers


interface ExpensesApi {


    companion object {
        const val BASE_URL = "https://run.mocky.io/v3/"
    }

    @Headers("Accept: application/json")
    @GET("178cbbee-c634-4a51-afb8-dcd75c190d29")
    suspend fun getExpenses(): Response<List<ExpensesDTO>>

}

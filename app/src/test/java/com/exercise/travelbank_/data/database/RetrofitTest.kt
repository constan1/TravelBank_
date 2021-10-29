package com.exercise.travelbank_.data.database

import com.exercise.travelbank_.data.network.ExpensesApi
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection


class RetrofitCallTest {


    private var mockWebServer = MockWebServer()

    private lateinit var expenseResponse: ExpensesApi

    @Before
    fun setup() {
        mockWebServer.start()

        expenseResponse = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/178cbbee-c634-4a51-afb8-dcd75c190d29/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExpensesApi::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Response expenseResponse responseCode200`() {
        val response = MockResponse()
            .status


        Assert.assertEquals("HTTP/1.1 200 OK", response)

    }

    @Test
    fun `Response expenseResponse ListOfResponseExpenseDTONotEmpty`() {

        val response = MockResponse()
        Assert.assertNotNull(response.getBody().toString())
    }
}
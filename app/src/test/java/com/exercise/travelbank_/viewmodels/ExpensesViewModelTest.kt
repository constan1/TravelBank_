package com.exercise.travelbank_.viewmodels

import android.app.Application
import android.content.Context

import kotlinx.coroutines.Dispatchers
import com.exercise.travelbank_.data.Repository
import com.exercise.travelbank_.data.database.ExpensesTypeConverter

import com.exercise.travelbank_.data.database.FakeRepositoryTest
import com.exercise.travelbank_.data.datasources.LocalDataSource
import com.exercise.travelbank_.data.datasources.RemoteDataSource
import com.exercise.travelbank_.data.network.ExpensesApi

import mockwebserver3.MockWebServer
import kotlinx.coroutines.*

import kotlinx.coroutines.test.resetMain

import org.junit.*
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.powermock.api.mockito.PowerMockito

import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.File
import java.text.SimpleDateFormat

@ExperimentalCoroutinesApi
@RunWith(PowerMockRunner::class)
@PrepareForTest(android.text.format.DateFormat::class)
@PowerMockIgnore("javax.net.ssl.*")
class ExpensesViewModelTest{



    private var mockWebServer = MockWebServer()

    private lateinit var expensesViewModel: ExpensesViewModel
    private lateinit var repository: Repository
    private lateinit var localDataSource: LocalDataSource
    private lateinit var expenseApi : ExpensesApi
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var expenseTypeConverter: ExpensesTypeConverter




    @Before
    fun setup()
    {

        PowerMockito.mockStatic(android.text.format.DateFormat::class.java)
        val format = SimpleDateFormat()
        format.applyPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        PowerMockito.`when`(android.text.format.DateFormat.getDateFormat(
            ArgumentMatchers.any(
                Context::class.java
            )
        )).thenAnswer {
            format
        }


        mockWebServer.start()

        expenseApi = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/178cbbee-c634-4a51-afb8-dcd75c190d29/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExpensesApi::class.java)

        remoteDataSource = RemoteDataSource(expenseApi)
        localDataSource = LocalDataSource(FakeRepositoryTest())
        repository = Repository(remoteDataSource,localDataSource)
        expensesViewModel = ExpensesViewModel(repository, Application())
        expenseTypeConverter = ExpensesTypeConverter()

    }




    @Test
     fun `returnResponse handleReturnedResponse NotNull`() = runBlocking{

        val response =  expensesViewModel.handleReturnedResponse(expenseApi.getExpenses()).data
        Assert.assertNotNull(response)

    }


    @Test
    fun `returnResponse ReturnedResponse isSuccessfull`() = runBlocking{
        val response = expenseApi.getExpenses().isSuccessful

        Assert.assertTrue(response)
    }


    @Test
     fun `cacheExpenses cacheRemoteExpenses expensesCachedSuccessfully`() = runBlocking {

        val response =  expensesViewModel.handleReturnedResponse(expenseApi.getExpenses()).data
        expensesViewModel.cacheRemoteExpenses(response!!)

        Assert.assertNotNull(localDataSource.getAllExpenses())
    }


    @Test
      fun `dateConverter convertDate dateConvertedSuccessfully`() = runBlocking{


        val date = expensesViewModel.dateConverter("2021-07-13T00:00:00.000Z")
        Assert.assertEquals("Jul. 13,2021",date)
    }

    @Test
    fun `typeConverter expenseConverter convertToString`() = runBlocking{
        val response =  expensesViewModel.handleReturnedResponse(expenseApi.getExpenses()).data

        val bufferedReader: BufferedReader = File("" +
                "C:\\Users\\Andrei\\Desktop\\TravelBank_\\app\\src\\test\\java\\com\\" +
                "exercise\\travelbank_\\response\\response.json").bufferedReader()

        val reader = bufferedReader.use{it.readText()}

        Assert.assertEquals(reader, expenseTypeConverter.expenseresponseToString(response!!))
    }

    @Test
    fun `typeConverter expenseConverter convertToDTO`() = runBlocking {

        val response =  expensesViewModel.handleReturnedResponse(expenseApi.getExpenses()).data

        val bufferedReader: BufferedReader = File("" +
                "C:\\Users\\Andrei\\Desktop\\TravelBank_\\app\\src\\test\\java\\com\\" +
                "exercise\\travelbank_\\response\\response.json").bufferedReader()

        val reader = bufferedReader.use{it.readText()}

       Assert.assertEquals(response,expenseTypeConverter.stringToExpenseResponse(reader))
    }

    @After
    fun teardown(){
        mockWebServer.shutdown()
        Dispatchers.resetMain()
    }


}
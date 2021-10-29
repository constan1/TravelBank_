package com.exercise.travelbank_.viewmodels

import android.app.Application
import com.exercise.travelbank_.data.Repository
import com.exercise.travelbank_.data.database.ExpensesTypeConverter
import com.exercise.travelbank_.data.database.FakeRepositoryTest
import com.exercise.travelbank_.data.datasources.LocalDataSource
import com.exercise.travelbank_.data.datasources.RemoteDataSource
import com.exercise.travelbank_.data.network.ExpensesApi
import mockwebserver3.MockWebServer
import kotlinx.coroutines.*
import org.junit.*
import org.powermock.core.classloader.annotations.PrepareForTest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.File

@ExperimentalCoroutinesApi
@PrepareForTest(android.text.format.DateFormat::class)
class ExpensesViewModelTest {


    private var mockWebServer = MockWebServer()

    private lateinit var expensesViewModel: ExpensesViewModel
    private lateinit var repository: Repository
    private lateinit var localDataSource: LocalDataSource
    private lateinit var expenseApi: ExpensesApi
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var expenseTypeConverter: ExpensesTypeConverter


    @Before
    fun setup() {

        mockWebServer.start()

        expenseApi = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/178cbbee-c634-4a51-afb8-dcd75c190d29/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExpensesApi::class.java)

        remoteDataSource = RemoteDataSource(expenseApi)
        localDataSource = LocalDataSource(FakeRepositoryTest())
        repository = Repository(remoteDataSource, localDataSource)
        expensesViewModel = ExpensesViewModel(repository, Application())
        expenseTypeConverter = ExpensesTypeConverter()

    }


    @Test
    fun `returnResponse handleReturnedResponse NotNull`() = runBlocking {

        val response = expensesViewModel.handleReturnedResponse(expenseApi.getExpenses()).data
        Assert.assertNotNull(response)

    }


    @Test
    fun `returnResponse ReturnedResponse isSuccessfull`() = runBlocking {
        val response = expenseApi.getExpenses().isSuccessful

        Assert.assertTrue(response)
    }


    @Test
    fun `cacheExpenses cacheRemoteExpenses expensesCachedSuccessfully`() = runBlocking {

        val response = expensesViewModel.handleReturnedResponse(expenseApi.getExpenses()).data
        expensesViewModel.cacheRemoteExpenses(response!!)

        Assert.assertNotNull(localDataSource.getAllExpenses())
    }


    @Test
    fun `typeConverter expenseConverter convertToString`() = runBlocking {
        val response = expensesViewModel.handleReturnedResponse(expenseApi.getExpenses()).data

        val bufferedReader: BufferedReader = File(
            "" +
                    "C:\\Users\\Andrei\\Desktop\\TravelBank_\\app\\src\\test\\java\\com\\" +
                    "exercise\\travelbank_\\response\\response.json"
        ).bufferedReader()

        val reader = bufferedReader.use { it.readText() }

        Assert.assertEquals(reader, expenseTypeConverter.expenseresponseToString(response!!))
    }

    @Test
    fun `typeConverter expenseConverter convertToDTO`() = runBlocking {

        val response = expensesViewModel.handleReturnedResponse(expenseApi.getExpenses()).data

        val bufferedReader: BufferedReader = File(
            "" +
                    "C:\\Users\\Andrei\\Desktop\\TravelBank_\\app\\src\\test\\java\\com\\" +
                    "exercise\\travelbank_\\response\\response.json"
        ).bufferedReader()

        val reader = bufferedReader.use { it.readText() }

        Assert.assertEquals(response, expenseTypeConverter.stringToExpenseResponse(reader))
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }


}
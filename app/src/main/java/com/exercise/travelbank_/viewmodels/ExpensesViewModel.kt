package com.exercise.travelbank_.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.exercise.travelbank_.data.Repository
import com.exercise.travelbank_.data.database.ExpensesEntity
import com.exercise.travelbank_.models.ExpensesDTO
import com.exercise.travelbank_.util.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject
import kotlin.math.expm1

@HiltViewModel
class ExpensesViewModel @Inject constructor(

    private val repository: Repository,
    application: Application

) : AndroidViewModel(application)
{

    /**Local Cached Data */

    var readExpenses: LiveData<List<ExpensesEntity>> = repository.localData.getAllExpenses().asLiveData()


    private fun cacheExpenses(expensesEntity: ExpensesEntity) =
        viewModelScope.launch(Dispatchers.IO){
            repository.localData.cacheExpenses(expensesEntity)
        }


    /**Remote Data */

    var expensesResponse: MutableLiveData<NetworkResource<List<ExpensesDTO>>> = MutableLiveData()


    fun getExpenses() = viewModelScope.launch {

        getExpensesCallResult()
    }

   private suspend fun getExpensesCallResult(){
       expensesResponse.value = NetworkResource.Loading()

       if(checkInternetConnection()){
           try {
               val response = repository.remoteData.getExpenses()
               expensesResponse.value = handleReturnedResponse(response)

               val expensesResponse_ = expensesResponse.value!!.data


               if(expensesResponse_!=null){
                   cacheRemoteExpenses(expensesResponse_)
               }

           } catch (e:Exception){
               expensesResponse.value = NetworkResource.Error(e.message.toString())
           }
       }
       else
       {
          expensesResponse.value = NetworkResource.Error("No internet Connection")
       }
   }

    private fun cacheRemoteExpenses(expensesResponse_: List<ExpensesDTO>) {

        val expensesEntity = ExpensesEntity(expensesResponse_)
        cacheExpenses(expensesEntity)
    }

    private fun handleReturnedResponse(response: Response<List<ExpensesDTO>>): NetworkResource<List<ExpensesDTO>>? {

        return when {
            response.code() == 404 ->{
                NetworkResource.Error("404 - Not Found")
            }
            response.isSuccessful ->{
                val expensesResponse = response.body()
                NetworkResource.Success(expensesResponse!!)
            }
            else -> {
                NetworkResource.Error(response.message())
            }
        }
    }

    private fun checkInternetConnection(): Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}
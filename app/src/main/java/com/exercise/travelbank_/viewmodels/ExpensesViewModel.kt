package com.exercise.travelbank_.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.format.DateFormat
import androidx.lifecycle.*
import coil.load
import com.exercise.travelbank_.R
import com.exercise.travelbank_.data.Repository
import com.exercise.travelbank_.data.database.ExpensesEntity
import com.exercise.travelbank_.databinding.FragmentExpenseDetailsBinding
import com.exercise.travelbank_.models.ExpensesDTO
import com.exercise.travelbank_.util.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(

    private val repository: Repository,
    application: Application?

) : AndroidViewModel(application!!)
{

    /**Local Cached Data */

    var readExpenses: LiveData<List<ExpensesEntity>> = repository.localData!!.getAllExpenses().asLiveData()


    private fun cacheExpenses(expensesEntity: ExpensesEntity) =
        viewModelScope.launch(Dispatchers.IO){
            repository.localData!!.cacheExpenses(expensesEntity)
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
               val response = repository.remoteData!!.getExpenses()
               expensesResponse.value = handleReturnedResponse(response)

               val expensesResponseValue = expensesResponse.value!!.data


               if(expensesResponseValue!=null){
                   cacheRemoteExpenses(expensesResponseValue)
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

     fun cacheRemoteExpenses(expensesResponse_: List<ExpensesDTO>) {

        val expensesEntity = ExpensesEntity(expensesResponse_)
        cacheExpenses(expensesEntity)
    }

     fun handleReturnedResponse(response: Response<List<ExpensesDTO>>): NetworkResource<List<ExpensesDTO>> {

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



    @SuppressLint("SimpleDateFormat")
    fun dateConverter(date: String) : String{
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

        val convertedDate: Date = format.parse(date)!!
        val month = DateFormat.format("MMM", convertedDate) as String?
        val day = DateFormat.format("dd", convertedDate) as String?
        val year = DateFormat.format("yyyy",convertedDate) as String?

        return "$month $day, $year"


    }

    fun loadImage( binding: FragmentExpenseDetailsBinding, image: String?){
        if(image == null){
            binding.listImage.load(R.drawable.ic_image_error){
                crossfade(600)
            }
        }
        else {
            binding.listImage.load(image){
                crossfade(600)
            }
        }
    }

}
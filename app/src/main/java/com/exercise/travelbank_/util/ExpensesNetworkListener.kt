package com.exercise.travelbank_.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalCoroutinesApi
class ExpensesNetworkListener: ConnectivityManager.NetworkCallback()  {
    private val isNetworkAvailable = MutableStateFlow(false)

    fun checkConnection(context: Context): MutableStateFlow<Boolean>{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)

        var isConnected= false


        connectivityManager.allNetworks.forEach { network ->
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            networkCapabilities?.let{
                if(it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                    isConnected = true
                    return@forEach
                }
            }
        }

        isNetworkAvailable.value = isConnected

        return isNetworkAvailable
    }

    override fun onLost(network: Network) {
        isNetworkAvailable.value = false
    }

    override fun onAvailable(network: Network) {
        isNetworkAvailable.value = true
    }

}
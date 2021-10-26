package com.exercise.travelbank_.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.exercise.travelbank_.R
import com.exercise.travelbank_.bindingadapters.adapters.ExpensesAdapter
import com.exercise.travelbank_.databinding.FragmentExpensesBinding
import com.exercise.travelbank_.util.ExpensesNetworkListener
import com.exercise.travelbank_.util.NetworkResource
import com.exercise.travelbank_.util.observeOnce
import com.exercise.travelbank_.viewmodels.ExpensesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ExpensesFragment : Fragment() {


    private var _binding: FragmentExpensesBinding? = null
    private val binding get() = _binding!!


    private lateinit var expensesViewModel: ExpensesViewModel
    private val mainAdapter by lazy { ExpensesAdapter() }

    private lateinit var expensesNetworkListener: ExpensesNetworkListener






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expensesViewModel = ViewModelProvider(requireActivity()).get(
            ExpensesViewModel::class.java
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentExpensesBinding.inflate(inflater,container,false)

        activity?.title = "Expenses"
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)

        setUpList()

        lifecycleScope.launch {
           expensesNetworkListener = ExpensesNetworkListener()
            if(expensesNetworkListener.internetConnection(requireContext())){
                readCachedExpenses()
            }
            else {
                readCachedExpenses()
                Toast.makeText(requireContext(),"Please Check Your Network Connection & Retry",Toast.LENGTH_LONG).show()
            }

        }

        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                expensesNetworkListener = ExpensesNetworkListener()
                if(expensesNetworkListener.internetConnection(requireContext())){
                    requestDataAfterRefresh()
                    binding.swipeRefresh.isRefreshing = false

                }
                else {
                    Toast.makeText(requireContext(),"Please Check Your Network Connection & Retry",Toast.LENGTH_LONG).show()
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }




        return binding.root

    }
    private fun setUpList(){
        val recyclerViewRoot = binding.root.findViewById<RecyclerView>(R.id.expenses_list)

        recyclerViewRoot.adapter = mainAdapter
        recyclerViewRoot.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun readCachedExpenses(){


        lifecycleScope.launch {
            expensesViewModel.readExpenses.observeOnce(viewLifecycleOwner, {
                database->
                if(database.isNotEmpty()){

                   var totalPrice = 0.0
                    mainAdapter.setData(database[0].expensesResponse)

                    for(amount in database[0].expensesResponse){
                        totalPrice +=amount.amount
                    }
                    binding.totalAmount.text = totalPrice.toString()

                }
                else {
                    requestRemoteData()
                }

            })
        }

    }

    private fun requestDataAfterRefresh(){


        expensesViewModel.getExpenses()

        readCachedExpenses()
    }

    private fun requestRemoteData() {

        expensesViewModel.getExpenses()
        expensesViewModel.expensesResponse.observe(viewLifecycleOwner,{
            response ->
            when(response){
                is NetworkResource.Success -> {

                    var totalPrice = 0.0
                    response.data?.let {
                            for(amount in it){
                                totalPrice += amount.amount

                        }

                        binding.totalAmount.text = totalPrice.toString()
                        mainAdapter.setData(it)}

                }
                is NetworkResource.Error -> {

                    loadLocalCacheData()
                    Toast.makeText(
                        requireContext(),
                       response.data?.get(0).toString(),
                        Toast.LENGTH_SHORT
                    ).show()


                }
                is NetworkResource.Loading -> {

                }
            }
        })
    }

    private fun loadLocalCacheData() {
        lifecycleScope.launch {
            expensesViewModel.readExpenses.observe(viewLifecycleOwner, {database ->
                if(database.isNotEmpty()){
                    mainAdapter.setData(database[0].expensesResponse)
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}


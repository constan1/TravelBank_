package com.exercise.travelbank_.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.exercise.travelbank_.R
import com.exercise.travelbank_.databinding.FragmentExpensesBinding

class ExpensesFragment : Fragment() {


    private var _binding: FragmentExpensesBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentExpensesBinding.inflate(inflater,container,false)

        activity?.title = "Expenses"




        return binding.root

    }

}
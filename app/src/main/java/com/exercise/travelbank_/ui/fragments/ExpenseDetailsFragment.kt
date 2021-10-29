package com.exercise.travelbank_.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.exercise.travelbank_.databinding.FragmentExpenseDetailsBinding
import com.exercise.travelbank_.viewmodels.ExpensesViewModel

class ExpenseDetailsFragment : Fragment() {


    private var _binding: FragmentExpenseDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var expensesViewModel: ExpensesViewModel


    private val args by navArgs<ExpenseDetailsFragmentArgs>()


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


        _binding = FragmentExpenseDetailsBinding.inflate(inflater, container, false)

        binding.editTextMerchantTitle.setText(args.expenses.expenseVenueTitle)
        binding.editTextAmount.setText(args.expenses.amount.toString())
        binding.editTextdate.setText(expensesViewModel.dateConverter(args.expenses.date))
        binding.ccategoryValue.text = args.expenses.tripBudgetCategory
        binding.currencyChip.text = args.expenses.currencyCode
        binding.editTextDetailsDescription.setText(args.expenses.description)

        binding.close.setOnClickListener {
            val action =
                ExpenseDetailsFragmentDirections.actionExpenseDetailsFragmentToExpensesFragment()
            findNavController().navigate(action)
        }

        expensesViewModel.loadImage(binding, args.expenses.attachments?.get(0)?.thumbnails?.list)

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
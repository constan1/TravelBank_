package com.exercise.travelbank_.bindingadapters

import android.annotation.SuppressLint
import android.net.Network
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.exercise.travelbank_.R
import com.exercise.travelbank_.models.Attachments
import java.text.SimpleDateFormat
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.exercise.travelbank_.data.database.ExpensesEntity
import com.exercise.travelbank_.models.ExpensesDTO
import com.exercise.travelbank_.ui.fragments.ExpensesFragmentDirections
import com.exercise.travelbank_.util.NetworkResource

import java.util.*

class ExpensesBinding {

    companion object {




        @BindingAdapter("onExpenseToDetails")
        @JvmStatic
        fun onExpensesToDetails(expensesRow: ConstraintLayout, expenses:ExpensesDTO){

            expensesRow.setOnClickListener {
                try {
                    val action = ExpensesFragmentDirections.actionExpensesFragmentToExpenseDetailsFragment(expenses)
                    expensesRow.findNavController().navigate(action)
                } catch (e: Exception){

                }
            }
        }


        @BindingAdapter("loadThumbNail_")
        @JvmStatic
        fun loadThumbNail(imageView: ImageView, attachements: List<Attachments>?) {

            if(attachements == null){
                imageView.load(R.drawable.ic_image_error){
                    crossfade(600)
                }
            }
            else {
                imageView.load( attachements[0].thumbnails.list) {
                    crossfade(600)
                }
            }

        }

        @BindingAdapter("setMerchantName")
        @JvmStatic
        fun setMerchantName(textView: TextView, title: String) {

            textView.text = title
        }

        @SuppressLint("SimpleDateFormat")
        @BindingAdapter("setDate")
        @JvmStatic
        fun setDate(textView: TextView, title: String) {


            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

            val date: Date = format.parse(title)!!
            val month = DateFormat.format("MMM", date) as String
            val day = DateFormat.format("dd", date) as String

            ("$month $day").also { textView.text = it }

        }

        @BindingAdapter("setCurrency")
        @JvmStatic
        fun setCurrency(textView: TextView, title: String) {
            textView.text = title
        }

        @BindingAdapter("setAmount")
        @JvmStatic
        fun setAmount(textView: TextView, amount: Double) {
            textView.text = amount.toString()
        }



        @BindingAdapter("setCategory")
        @JvmStatic
        fun setCategory(textView: TextView, category: String) {
            textView.text = category

        }


        @BindingAdapter("networkResponseForImage","loadDataForImage",requireAll = true)
        @JvmStatic
        fun errorNoInternetImageView(
            imageView: ImageView,
            apiResponse:NetworkResource<List<ExpensesDTO>>?,
            database: List<ExpensesEntity>?
        ) {
            if(apiResponse is NetworkResource.Error && database.isNullOrEmpty()){
                imageView.visibility = View.VISIBLE
            }
            else if(apiResponse is NetworkResource.Loading){
                imageView.visibility = View.INVISIBLE
            }
            else if(apiResponse is NetworkResource.Success){
                imageView.visibility = View.INVISIBLE
            }
        }
        @BindingAdapter("networkResponseForText", "loadDataForText",requireAll = true)
        @JvmStatic
        fun errorNoInternetTextview(
            textView: TextView,
            apiResponse: NetworkResource<List<ExpensesDTO>>?,
            database: List<ExpensesEntity>?
        )
        {
            if(apiResponse is NetworkResource.Error && database.isNullOrEmpty()){
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()

            }
            else if (apiResponse is NetworkResource.Loading)
            {
                textView.visibility = View.INVISIBLE

            }
            else if(apiResponse is NetworkResource.Success){

                textView.visibility = View.INVISIBLE

            }
        }

        @BindingAdapter("networkResponseForTextTotal", "loadDataForTextTotal",requireAll = true)
        @JvmStatic
        fun errorNoInternetTotalTextview(
            textView: TextView,
            apiResponse: NetworkResource<List<ExpensesDTO>>?,
            database: List<ExpensesEntity>?
        )
        {
            if(apiResponse is NetworkResource.Error && database.isNullOrEmpty()){
                textView.visibility = View.INVISIBLE

            }
            else if (apiResponse is NetworkResource.Loading)
            {
                textView.text = "Loading..."
                textView.visibility = View.VISIBLE

            }
            else if(apiResponse is NetworkResource.Success){

                textView.text = "Total: "
                textView.visibility = View.VISIBLE


            }
        }


    }



}
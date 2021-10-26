package com.exercise.travelbank_.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.exercise.travelbank_.R
import com.exercise.travelbank_.models.Attachments
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ExpensesBinding {

    companion object {

        @BindingAdapter("loadThumbNail")
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

        @BindingAdapter("setDate")
        @JvmStatic
        fun setDate(textView: TextView, title: String) {
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

            val dateFormatted = title.format(formatter)

            textView.text = dateFormatted

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
    }

}
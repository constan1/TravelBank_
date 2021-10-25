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
        fun loadThumbNail(imageView: ImageView, attachments: Attachments) {

            imageView.load( attachments.thumbnails[0].list) {
                crossfade(600)
                error(R.drawable.ic_image_error)
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
            val unconvertedDate = title
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

            val dateFormatted = unconvertedDate.format(formatter)

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
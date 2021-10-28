package com.exercise.travelbank_.data.database

import androidx.room.TypeConverter
import com.exercise.travelbank_.models.ExpensesDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExpensesTypeConverter {

    var gson = Gson()


    @TypeConverter
    fun expenseresponseToString(expensesResponse:  List<ExpensesDTO>): String{
        return gson.toJson(expensesResponse)
    }

    @TypeConverter
    fun stringToExpenseResponse(data: String):List<ExpensesDTO>{
        val listType = object: TypeToken<List<ExpensesDTO>>() {}.type
        return gson.fromJson(data,listType)
    }



}
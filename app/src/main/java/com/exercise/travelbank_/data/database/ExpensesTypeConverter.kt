package com.exercise.travelbank_.data.database

import androidx.room.TypeConverter
import com.exercise.travelbank_.data.ExpensesResponse
import com.exercise.travelbank_.models.ExpensesDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExpensesTypeConverter {

    var gson = Gson()


    @TypeConverter
    fun expenseresponseToString(expensesResponse: ExpensesResponse): String{
        return gson.toJson(expensesResponse)
    }

    @TypeConverter
    fun stringToExpenseResponse(data: String):ExpensesResponse{
        val listType = object: TypeToken<ExpensesResponse>() {}.type
        return gson.fromJson(data,listType)
    }

    @TypeConverter
    fun ExpensesDtoToString(expensesDTO: ExpensesDTO) : String{
        return gson.toJson(expensesDTO)
    }

    fun stringToExpensesDto(data: String): ExpensesDTO{
        val listType = object: TypeToken<ExpensesDTO>() {}.type
        return gson.fromJson(data,listType)
    }
}
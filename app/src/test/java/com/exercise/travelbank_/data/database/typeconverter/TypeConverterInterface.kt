package com.exercise.travelbank_.data.database.typeconverter

import com.exercise.travelbank_.models.ExpensesDTO

interface TypeConverterInterface {

    fun expenseresponseToString(expensesResponse:  List<ExpensesDTO>): String

    fun stringToExpenseResponse(data: String):List<ExpensesDTO>
}
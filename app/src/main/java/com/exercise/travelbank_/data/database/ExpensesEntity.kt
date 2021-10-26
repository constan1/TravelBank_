package com.exercise.travelbank_.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

import com.exercise.travelbank_.models.ExpensesDTO



@Entity(tableName = "Expenses")
class ExpensesEntity(

    var expensesResponse: List<ExpensesDTO>
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
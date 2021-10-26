package com.exercise.travelbank_.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensesDAO {

    @Query("SELECT * FROM Expenses ORDER BY id ASC")
    fun getAllExpenses(): Flow<List<ExpensesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheExpenses(expensesEntity: ExpensesEntity)

    @Query("DELETE FROM Expenses")
    suspend fun deleteAllExpenses()


}
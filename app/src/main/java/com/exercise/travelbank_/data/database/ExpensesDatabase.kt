package com.exercise.travelbank_.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters


@Database(
    entities = [ExpensesEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(ExpensesTypeConverter::class)
abstract class ExpensesDatabase : RoomDatabase() {

    abstract fun expensesDao(): ExpensesDAO
}
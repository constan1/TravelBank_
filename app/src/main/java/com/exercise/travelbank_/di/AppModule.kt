package com.exercise.travelbank_.di

import android.app.Application
import androidx.room.Room
import com.exercise.travelbank_.data.database.ExpensesDatabase
import com.exercise.travelbank_.data.network.ExpensesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(ExpensesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesExpensesApi(retrofit: Retrofit): ExpensesApi =
        retrofit.create(ExpensesApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(application:Application): ExpensesDatabase =
      Room.databaseBuilder(application, ExpensesDatabase::class.java,
      "expenses_database")
          .fallbackToDestructiveMigration()
          .build()
}
package com.exercise.travelbank_.di

import android.app.Application
import androidx.room.Room
import com.exercise.travelbank_.data.database.ExpensesDatabase
import com.exercise.travelbank_.data.network.ExpensesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(ExpensesApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun providesExpensesApi(retrofit: Retrofit): ExpensesApi =
        retrofit.create(ExpensesApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(application: Application): ExpensesDatabase =
        Room.databaseBuilder(
            application, ExpensesDatabase::class.java,
            "expenses_database"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideDao(database: ExpensesDatabase) = database.expensesDao()
}
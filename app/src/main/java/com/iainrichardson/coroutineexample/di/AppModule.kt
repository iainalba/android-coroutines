package com.iainrichardson.coroutineexample.di

import android.content.Context
import com.iainrichardson.coroutineexample.App
import com.iainrichardson.coroutineexample.api.AnimalFactsService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun context(application: App): Context = application

    @Provides
    @Singleton
    fun retrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)
        return Retrofit.Builder()
                .baseUrl("https://cat-fact.herokuapp.com")
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

    @Provides
    @Singleton
    fun animalFactsService(retrofit: Retrofit): AnimalFactsService =
            retrofit.create(AnimalFactsService::class.java)


}

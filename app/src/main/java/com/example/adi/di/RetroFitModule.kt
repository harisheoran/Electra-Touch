package com.example.adi.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.adi.AdiApplication
import com.example.adi.network.HinduismService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetroFitModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.jsonbin.io")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): HinduismService {
        return retrofit.create(HinduismService::class.java)
    }

    @Singleton
    @Provides
    fun getOkHttpClient(): OkHttpClient{
        val client = OkHttpClient.Builder()
        client.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })

        client.addInterceptor(
            ChuckerInterceptor.Builder(AdiApplication.myContext)
                .collector(ChuckerCollector(AdiApplication.myContext))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )
        return client.build()
    }
}
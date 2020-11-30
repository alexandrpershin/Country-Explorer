package com.alexandrpershin.country.explorer.api


import android.content.Context
import com.alexandrpershin.country.explorer.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Helper class to create API services
 * */

class BackendApiFactory {

    private val gson: Gson = GsonBuilder().create()

    private val gsonFactory: GsonConverterFactory = GsonConverterFactory.create(gson)

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        // print in console all request/response
        val consoleInterceptor = HttpLoggingInterceptor().also {
            it.level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }

        return consoleInterceptor
    }


    fun provideRetrofit(application: Context): Retrofit {
        val retrofit =
            Retrofit.Builder()
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(provideOkHttpClient(application))
                .baseUrl(BASE_URL)
                .build()

        return retrofit
    }

    private fun provideOkHttpClient(application: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(150, TimeUnit.SECONDS)
            .readTimeout(150, TimeUnit.SECONDS)
            .addInterceptor(provideHttpLoggingInterceptor())

        if (BuildConfig.DEBUG) builder.addInterceptor(ChuckInterceptor(application))

        return builder.build()
    }

    companion object {
        private const val BASE_URL = "https://restcountries.eu/"
    }

}

inline fun <reified T> createApiService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}
package io.github.yoobi.sampleviewbinding.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


val clientBuilder: OkHttpClient.Builder by lazy {

    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
}

object Api {

    enum class ApiStatus(val show: Boolean) { LOADING(true), SUCCESS(false), ERROR(false) }

    private const val TVMAZE_URL = "http://api.tvmaze.com/search/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(clientBuilder.build())
            .baseUrl(TVMAZE_URL)
            .build()
    }

    val tvMazeService: TvMazeService by lazy {
        retrofit().create(TvMazeService::class.java)
    }
}
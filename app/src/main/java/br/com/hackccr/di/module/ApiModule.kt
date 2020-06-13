package br.com.hackccr.di.module

import android.content.Context
import android.util.Log
import br.com.hackccr.BuildConfig
import br.com.hackccr.api.Api
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    private var apiTimeoutInSeconds: Int = 0

    init {
        if (!BuildConfig.DEBUG && "production" == BuildConfig.FLAVOR) {
            this.apiTimeoutInSeconds = 45
        } else {
            this.apiTimeoutInSeconds = 60
        }
    }

    @Provides
    @Singleton
    fun provideApi(factory: Converter.Factory, client: OkHttpClient) : Api {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(factory)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()

        return retrofit.create(Api::class.java)
    }

    @Provides
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(apiTimeoutInSeconds.toLong(), TimeUnit.SECONDS)
                .readTimeout(apiTimeoutInSeconds.toLong(), TimeUnit.SECONDS)
                .writeTimeout(apiTimeoutInSeconds.toLong(), TimeUnit.SECONDS)
                .cache(cache)
                .build()
    }

    @Provides
    fun provideOkHttpCache(context: Context): Cache {
        val cacheFile = File(context.cacheDir, "http-cache")
        val cacheSize = (10 * 1024 * 1024).toLong()
        return Cache(cacheFile, cacheSize)
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor { message -> Log.i("", message) }
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    fun provideConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }
}
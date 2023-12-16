package com.surelabsid.mrjempootdriver.dagger_hilt

import android.util.Base64
import android.util.Log
import com.surelabsid.mrjempootdriver.utils.SessionManager
import com.surelabsid.mrjempootdriver.network.ApiService
import com.surelabsid.mrjempootdriver.network.ApiServiceVerihubs
import com.surelabsid.mrjempootdriver.ui.beranda.modelresponse.AppSettings
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.BASE_URL
import com.surelabsid.mrjempootdriver.utils.Constant.Companion.url.BASE_URL_VERIHUBS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkhttpClient(session: SessionManager): OkHttpClient {

        val credentials = session.username + ":" + session.password
        val basic = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder().addInterceptor(interceptor).apply {
            readTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            addInterceptor { chain ->
                val original = chain.request()
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", basic)
                    .addHeader("Accept", "application/json")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideOkhttpClientVerihubs(session: SessionManager): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY


        Log.d("TAG", "provideOkhttpClientVerihubs: ${session.app_settings}")

        var appSettings = AppSettings()
        if (session.app_settings != "") {
            appSettings = Json.decodeFromString<AppSettings>(session.app_settings)
        }

        Log.d("TAG", "provideOkhttpClientVerihubs: ${appSettings.verihubsAppId}")

        val builder = OkHttpClient.Builder().addInterceptor(interceptor).apply {
            readTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            addInterceptor { chain ->
                val original = chain.request()
                val request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    // Fixme use prod key for prod app
                    .addHeader("App-ID", "${appSettings.verihubsAppId}")
//                    .addHeader("App-ID", "${appSettings.verihubsAppIdSb}")
//                    .addHeader("App-ID", session.app_id_verihubs)
                    .addHeader("API-Key", "${appSettings.verihubsKey}")
//                    .addHeader("API-Key", "${appSettings.verihubsKeySb}")
//                    .addHeader("API-Key", session.api_key_verihubs)
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideApiService(session: SessionManager): ApiService {

        val okHttpClient = provideOkhttpClient(session)

        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)

    }

    @Provides
    @Singleton
    fun provideApiServiceVerihubs(session: SessionManager): ApiServiceVerihubs {

        val okHttpClient = provideOkhttpClientVerihubs(session)

        return Retrofit.Builder().baseUrl(BASE_URL_VERIHUBS)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiServiceVerihubs::class.java)

    }

    @Provides
    @Singleton
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

}
package com.example.workflow_manager_frontend.data.source.network
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    //for testing purposes don't rely on these
    private val emulatorBaseUrl = "http://10.0.2.2:8080"
    private val hostBaseUrl = "http://192.168.56.162:8080"

    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(JwtInterceptor())
        .build()

    val api: ApiClient by lazy {
        Retrofit.Builder()
            .baseUrl(emulatorBaseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiClient::class.java)
    }
}
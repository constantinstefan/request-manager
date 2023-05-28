package com.example.workflow_manager_frontend.data.source.network

import com.example.workflow_manager_frontend.data.repository.JwtRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response


class JwtInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        if(originalRequest.url().encodedPath().startsWith("/api/v1/auth")) {
            return chain.proceed(originalRequest)
        }
        if(originalRequest.url().encodedPath().startsWith("/api/v1/customers")
            && originalRequest.method().equals("POST")) {
            return chain.proceed(originalRequest)
        }

        val jwt = runBlocking {
            JwtRepositoryInstance.instance?.getJwt()
        }

        val modifiedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer ${jwt?.token}")
            .build()

        return chain.proceed(modifiedRequest)
    }
}
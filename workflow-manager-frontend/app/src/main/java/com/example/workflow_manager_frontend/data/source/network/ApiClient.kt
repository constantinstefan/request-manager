package com.example.workflow_manager_frontend.data.source.network

import com.example.workflow_manager_frontend.domain.Customer
import com.example.workflow_manager_frontend.domain.Jwt
import com.example.workflow_manager_frontend.domain.Workflow
import com.example.workflow_manager_frontend.domain.request.AuthenticationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiClient {

    @POST("/api/v1/auth")
    suspend fun login(@Body authenticationRequest: AuthenticationRequest) : Response<Jwt>

    @GET("/api/v1/principal")
    suspend fun getPrincipal() : Response<Customer>

    @GET("/api/v1/customers/{customerId}/workflows")
    suspend fun getWorkflows(@Path("customerId") customerId: Int) : Response<List<Workflow>>

    @POST("/api/v1/customers")
    suspend fun postCustomers(@Body customer: Customer) : Response<Customer>
}
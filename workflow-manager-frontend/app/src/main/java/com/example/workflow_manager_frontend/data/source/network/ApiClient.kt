package com.example.workflow_manager_frontend.data.source.network

import com.example.workflow_manager_frontend.domain.*
import com.example.workflow_manager_frontend.domain.request.AuthenticationRequest
import com.example.workflow_manager_frontend.domain.request.ChangePasswordRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiClient {

    @POST("/api/v1/auth")
    suspend fun login(@Body authenticationRequest: AuthenticationRequest) : Response<Jwt>

    @GET("/api/v1/principal")
    suspend fun getPrincipal() : Response<Customer>

    @GET("/api/v1/customers/{customerId}/workflows")
    suspend fun getWorkflows(@Path("customerId") customerId: Int) : Response<List<Workflow>>

    @POST("/api/v1/customers")
    suspend fun postCustomers(@Body customer: Customer) : Response<Customer>

    @PATCH("/api/v1/customers/{customerId}/password")
    suspend fun patchCustomerPassword(@Path("customerId") customerId: Int, @Body changePasswordRequest: ChangePasswordRequest) : Response<Customer>

    @GET("/api/v1/workflows/{workflowId}/steps")
    suspend fun getWorkflowStepsByWorkflowId(@Path("workflowId") workflowId: Long) : Response<List<WorkflowStep>>

    @Multipart
    @POST("/api/v1/workflows/{workflowId}/execution")
    suspend fun executeWorkflow(
        @Path("workflowId") workflowId : Long,
        @Part("data") data: RequestBody,
        @Part file: List<MultipartBody.Part>
        ) : Response<Any>
}
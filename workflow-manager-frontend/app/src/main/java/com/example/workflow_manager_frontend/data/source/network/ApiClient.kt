package com.example.workflow_manager_frontend.data.source.network

import com.example.workflow_manager_frontend.domain.*
import com.example.workflow_manager_frontend.domain.request.*
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
    suspend fun patchCustomerPassword(
        @Path("customerId") customerId: Int,
        @Body changePasswordRequest: ChangePasswordRequest) : Response<Customer>

    @GET("/api/v1/groups/{groupId}")
    suspend fun getGroupById(@Path("groupId") groupId: Int) : Response<Group>

    @POST("/api/v1/workflows")
    suspend fun addWorkflow(@Body workflow: WorkflowRequest) : Response<Workflow>

    @POST("/api/v1/workflows/{workflowId}/sharing")
    suspend fun setWorkflowSharing(
        @Path("workflowId") workflowId: Int?,
        @Body sharing: SharingRequest) : Response<Sharing>

    @GET("/api/v1/workflows/{workflowId}/steps")
    suspend fun getWorkflowStepsByWorkflowId(@Path("workflowId") workflowId: Long) : Response<List<WorkflowStep>>

    @POST("/api/v1/workflows/{workflowId}/steps")
    suspend fun addWorkflowStep(
        @Path("workflowId") workflowId: Int?,
        @Body addWorkflowStepRequest: AddWorkflowStepRequest) : Response<WorkflowStep>

    @POST("/api/v1/workflows/{workflowId}/steps/{stepId}/document-request")
    suspend fun addDocumentRequest(
        @Path("workflowId") workflowId: Int?,
        @Path("stepId") stepId: Int?,
        @Body document: Document
    )

    @POST("/api/v1/workflows/{workflowId}/steps/{stepId}/form-fields")
    suspend fun addFormFields(
        @Path("workflowId") workflowId: Int?,
        @Path("stepId") stepId: Int?,
        @Body formField: List<FormField>
    )

    @POST("/api/v1/workflows/{workflowId}/steps/{stepId}/email")
    suspend fun addEmailStep(
        @Path("workflowId") workflowId: Int?,
        @Path("stepId") stepId: Int?,
        @Body email: Email
    )

    @Multipart
    @POST("/api/v1/workflows/{workflowId}/steps/{stepId}/editable-html")
    suspend fun addEditableHtml(
        @Path("workflowId") workflowId: Int?,
        @Path("stepId") stepId: Int?,
        @Part("data") data: RequestBody,
        @Part file: MultipartBody.Part
    )

    @Multipart
    @POST("/api/v1/workflows/{workflowId}/execution")
    suspend fun executeWorkflow(
        @Path("workflowId") workflowId : Long,
        @Part("data") data: RequestBody,
        @Part file: List<MultipartBody.Part>
        ) : Response<Any>
}
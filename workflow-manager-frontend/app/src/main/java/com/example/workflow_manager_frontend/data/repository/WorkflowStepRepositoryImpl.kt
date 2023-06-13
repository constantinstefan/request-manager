package com.example.workflow_manager_frontend.data.repository
import android.net.Uri
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.workflow_manager_frontend.data.source.db.WorkflowStepDao
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
import com.example.workflow_manager_frontend.domain.WorkflowStep
import com.example.workflow_manager_frontend.domain.request.AddEditableHtmlStepRequest
import com.example.workflow_manager_frontend.domain.request.AddWorkflowStepRequest
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class WorkflowStepRepositoryImpl @Inject constructor(
    private val workflowStepDao: WorkflowStepDao
): WorkflowStepRepository {

    private val tag: String = "WorkflowStepRepository"

    override suspend fun getWorkflowSteps(workflowId: Long, fetchFromRemote: Boolean): List<WorkflowStep>? {
        if(! fetchFromRemote) {
           return workflowStepDao.getWorkflowStepsByWorkflowId(workflowId)
        }

        try {
            val response = RetrofitInstance.api.getWorkflowStepsByWorkflowId(workflowId)
            if (!response.isSuccessful) {
                Log.e(tag, response.toString())
                return emptyList()
            }

            return response.body()
        } catch(e : Exception) {
            Log.e(tag, e.toString())
        }
        return null
    }

    override suspend fun createWorkflowStep(workflowId: Int?, step: WorkflowStep) {
        try{
            val response = RetrofitInstance.api.addWorkflowStep(workflowId,
            addWorkflowStepRequest = AddWorkflowStepRequest(step.stepNumber, step.stepType)
            )
            if(!response.isSuccessful) {
                Log.e(tag, response.toString())
                return
            }
            val stepId = response.body()?.workflowStepId
            when(step.stepType) {
                "DOCUMENT" -> {
                    step.document?.let { RetrofitInstance.api.addDocumentRequest(
                            workflowId, stepId, it)
                    }}
                "FORM_FIELDS" -> {
                    step.formFields?.let{RetrofitInstance.api.addFormFields(
                        workflowId, stepId, it)
                    }}
                "EMAIL" -> {
                    step.email?.let{ RetrofitInstance.api.addEmailStep(
                        workflowId, stepId, it)
                    }}
                "EDITABLE_HTML" -> {
                    addHtmlStep(workflowId, stepId, step)
                    }
                "CHATGPT" -> {
                    step.chatGptStep?.let{RetrofitInstance.api.addChatGptStep(
                        workflowId, stepId, it)
                    }}
            }

        } catch(e: Exception) {
            Log.e(tag, e.toString())
        }
    }

    private suspend fun addHtmlStep(workflowId: Int?,stepId: Int?, htmlStep: WorkflowStep) {
        if(htmlStep.editableHtml == null) {
            return
        }
        val jsonDataRequestBody = RequestBody.create(
            MediaType.parse("application/json"),
            Gson().toJson(AddEditableHtmlStepRequest(
                isRequired = htmlStep.editableHtml.isRequired,
                uploadedEditedHtmlFileVariable = htmlStep.editableHtml.uploadedEditedHtmlFileVariable,
                pdfResultVariable = htmlStep.editableHtml.pdfResultVariable
            )))

        val htmlFile = htmlStep.editableHtml.file
        var file : MultipartBody.Part? = null
        if(htmlFile!= null) {
            val requestBody = RequestBody.create(
                MediaType.parse("application/octet-stream"),
                htmlFile
            )
            file = MultipartBody.Part.createFormData(
                "file",
                htmlStep.editableHtml.fileName,
                requestBody
            )
        }
        else{
            //to be checked
            file = MultipartBody.Part.createFormData(
                "file",
                "",
                null
            )
        }
        RetrofitInstance.api.addEditableHtml(
            workflowId = workflowId,
            stepId = stepId,
            data = jsonDataRequestBody,
            file = file)
    }

    override suspend fun updateWorkflowStep(workflowId: Long, step: WorkflowStep) {
        TODO("Not yet implemented")
    }
}
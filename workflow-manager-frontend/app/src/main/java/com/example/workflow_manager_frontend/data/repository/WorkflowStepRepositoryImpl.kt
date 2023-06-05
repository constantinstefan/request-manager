package com.example.workflow_manager_frontend.data.repository
import android.util.Log
import com.example.workflow_manager_frontend.data.source.db.WorkflowStepDao
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
import com.example.workflow_manager_frontend.domain.WorkflowStep
import com.example.workflow_manager_frontend.domain.request.AddWorkflowStepRequest
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
                "FORM_FIELD" -> {
                    step.formFields?.let{RetrofitInstance.api.addFormFields(
                        workflowId, stepId, it)
                    }}
                "EMAIL" -> {
                    step.email?.let{ RetrofitInstance.api.addEmailStep(
                        workflowId, stepId, it)
                    }}
            }

        } catch(e: Exception) {
            Log.e(tag, e.toString())
        }
    }

    override suspend fun updateWorkflowStep(workflowId: Long, step: WorkflowStep) {
        TODO("Not yet implemented")
    }
}
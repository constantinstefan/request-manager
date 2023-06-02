package com.example.workflow_manager_frontend.data.repository
import android.util.Log
import com.example.workflow_manager_frontend.data.source.db.WorkflowStepDao
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
import com.example.workflow_manager_frontend.domain.WorkflowStep
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
}
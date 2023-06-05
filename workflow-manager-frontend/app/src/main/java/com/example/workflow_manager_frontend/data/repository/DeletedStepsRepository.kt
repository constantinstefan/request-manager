package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.domain.WorkflowStep

interface DeletedStepsRepository {
    fun addDeletedStepToCache(step: WorkflowStep)

    fun deleteAllStepsFromCache()

    fun deleteStepsRemote()
}
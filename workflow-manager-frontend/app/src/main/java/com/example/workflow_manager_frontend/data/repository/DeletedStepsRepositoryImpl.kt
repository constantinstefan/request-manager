package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.domain.WorkflowStep

class DeletedStepsRepositoryImpl : DeletedStepsRepository {

    private val deletedStepsCache: MutableList<WorkflowStep> = mutableListOf()

    override fun addDeletedStepToCache(step: WorkflowStep) {
        deletedStepsCache.add(step)
    }

    override fun deleteAllStepsFromCache() {
        deletedStepsCache.clear()
    }

    override fun deleteStepsRemote() {
        TODO("Not yet implemented")
    }
}
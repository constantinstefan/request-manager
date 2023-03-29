package com.example.workflow_manager_frontend.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.workflow_manager_frontend.domain.model.Workflow

@Database(entities = [Workflow::class], version = 1)
abstract class WorkflowDatabase : RoomDatabase() {
    abstract fun workflowDao(): WorkflowDao

    companion object {
        val DATABASE_NAME = "workflow_db"
    }
}
package com.example.workflow_manager_frontend.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.workflow_manager_frontend.data.source.db.WorkflowDao
import com.example.workflow_manager_frontend.domain.EditableHtml
import com.example.workflow_manager_frontend.domain.Jwt
import com.example.workflow_manager_frontend.domain.Workflow
import com.example.workflow_manager_frontend.domain.WorkflowStep
import com.example.workflow_manager_frontend.domain.converter.*

@Database(entities = [Workflow::class, Jwt::class, WorkflowStep::class], version = 5)
@TypeConverters(
    SharingTypeConverter::class,
    DocumentConverter::class,
    EditableHtmlConverter::class,
    FormFieldConverter::class,
    EmailConverter::class)
abstract class WorkflowDatabase : RoomDatabase() {
    abstract fun workflowDao(): WorkflowDao
    abstract fun jwtDao(): JwtDao

    abstract fun workflowStepDao(): WorkflowStepDao

    companion object {
        val DATABASE_NAME = "workflow_db"
    }
}
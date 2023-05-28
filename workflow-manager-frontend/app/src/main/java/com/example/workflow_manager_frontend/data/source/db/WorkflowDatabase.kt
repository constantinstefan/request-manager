package com.example.workflow_manager_frontend.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.workflow_manager_frontend.data.source.db.WorkflowDao
import com.example.workflow_manager_frontend.domain.Jwt
import com.example.workflow_manager_frontend.domain.Workflow
import com.example.workflow_manager_frontend.domain.converter.SharingTypeConverter

@Database(entities = [Workflow::class, Jwt::class], version = 2)
@TypeConverters(SharingTypeConverter::class)
abstract class WorkflowDatabase : RoomDatabase() {
    abstract fun workflowDao(): WorkflowDao
    abstract fun jwtDao(): JwtDao

    companion object {
        val DATABASE_NAME = "workflow_db"
    }
}
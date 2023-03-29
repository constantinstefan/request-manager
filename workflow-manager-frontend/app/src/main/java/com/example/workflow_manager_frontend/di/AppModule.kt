package com.example.workflow_manager_frontend.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workflow_manager_frontend.data.repository.WorkflowRepository
import com.example.workflow_manager_frontend.data.repository.WorkflowRepositoryImpl
import com.example.workflow_manager_frontend.data.repository.WorkflowRepositoryStub
import com.example.workflow_manager_frontend.data.source.WorkflowDatabase
import com.example.workflow_manager_frontend.presentation.main.home.WorkflowAdapter
import com.example.workflow_manager_frontend.presentation.main.home.WorkflowDiffCallback
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesWorkflowDatabase(app: Application) : WorkflowDatabase {
        return Room.databaseBuilder(
            app,
            WorkflowDatabase::class.java,
            WorkflowDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesWorkflowRepository(workflowDatabase: WorkflowDatabase): WorkflowRepository {
        //return WorkflowRepositoryImpl(workflowDatabase.workflowDao())
        return WorkflowRepositoryStub()
    }
}


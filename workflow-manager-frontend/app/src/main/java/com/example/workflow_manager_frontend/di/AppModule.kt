package com.example.workflow_manager_frontend.di

import android.app.Application
import androidx.room.Room
import com.example.workflow_manager_frontend.data.repository.*
import com.example.workflow_manager_frontend.data.source.db.WorkflowDatabase
import com.example.workflow_manager_frontend.data.source.network.JwtRepositoryInstance
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
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesJwtRepository(workflowDatabase: WorkflowDatabase) : JwtRepository {
        val jwtRepository: JwtRepository = JwtRepositoryImpl(workflowDatabase.jwtDao())
        JwtRepositoryInstance.setJwtRepository(jwtRepository)
        return jwtRepository
    }

    @Provides
    @Singleton
    fun providesWorkflowRepository(workflowDatabase: WorkflowDatabase): WorkflowRepository {
        return WorkflowRepositoryImpl(workflowDatabase.workflowDao())
        //return WorkflowRepositoryStub()
    }
}


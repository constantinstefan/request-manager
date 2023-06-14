package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.domain.Notification
import com.example.workflow_manager_frontend.domain.Workflow

interface NotificationRepository {
    suspend fun getNotifications(): List<Notification>?
}
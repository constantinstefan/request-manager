package com.example.workflow_manager_frontend.data.repository

import com.example.workflow_manager_frontend.domain.Customer

interface UserRepository {
    suspend fun getPrincipal(): Customer?
}
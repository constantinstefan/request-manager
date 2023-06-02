package com.example.workflow_manager_frontend.domain.request

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)
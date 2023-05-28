package com.example.workflow_manager_frontend.domain.request

data class AuthenticationRequest(
    val email: String,
    val password: String
)
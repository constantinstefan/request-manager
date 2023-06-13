package com.example.workflow_manager_frontend.domain

data class Customer(
    val email: String,
    val firstName: String?,
    val id: Int,
    val lastName: String?,
    val role: String,
    val password: String
) {
    override fun toString(): String {
        return email
    }
}
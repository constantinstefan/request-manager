package com.example.workflow_manager_frontend.domain

data class Notification(
    val id: Int,
    val isOpen: Boolean,
    val message: String,
    val time: Long
) {
    override fun toString(): String {
        return message
    }
}
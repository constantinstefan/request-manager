package com.example.workflow_manager_frontend.domain.request

data class SharingRequest(
    val sharingType: String,
    val groupId: Int?
)

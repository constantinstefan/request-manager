package com.example.workflow_manager_frontend.domain

data class Sharing(
    val groupId: Int = 0,
    val id: Int = 0,
    var sharingType: String = "",
    val workflowId: Int = 0,
    var group: Group? = null
)
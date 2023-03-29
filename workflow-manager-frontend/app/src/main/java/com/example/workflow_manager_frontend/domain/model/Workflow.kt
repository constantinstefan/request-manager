package com.example.workflow_manager_frontend.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workflow(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val type: String,
    val groupName: String? = null,
    val dueDate: String? = null,
)

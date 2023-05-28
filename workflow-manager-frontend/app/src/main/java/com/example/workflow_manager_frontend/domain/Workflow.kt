package com.example.workflow_manager_frontend.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workflow(
    val description: String,
    @PrimaryKey val id: Int?,
    val name: String,
    val sharing: Sharing
)
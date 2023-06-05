package com.example.workflow_manager_frontend.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workflow(
    var description: String = "",
    @PrimaryKey val id: Int? = 0,
    var name: String = "",
    val sharing: Sharing? = null
)
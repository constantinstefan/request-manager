package com.example.workflow_manager_frontend.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jwt_token")
data class Jwt(
    @PrimaryKey var id: Int = 1,
    val token: String
)
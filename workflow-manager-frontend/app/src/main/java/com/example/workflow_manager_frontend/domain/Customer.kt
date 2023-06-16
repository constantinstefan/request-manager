package com.example.workflow_manager_frontend.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Customer(
    val email: String,
    val firstName: String?,
    val id: Int,
    val lastName: String?,
    val role: String?,
    val password: String?
) : Parcelable {
    override fun toString(): String {
        return email
    }
}
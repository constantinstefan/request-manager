package com.example.workflow_manager_frontend.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    val description: String,
    val id: Int = 0,
    val name: String
) : Parcelable {
    override fun toString(): String {
        return name
    }
}
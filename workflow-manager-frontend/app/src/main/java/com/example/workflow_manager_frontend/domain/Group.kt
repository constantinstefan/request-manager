package com.example.workflow_manager_frontend.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    val description: String,
    val groupId: Int,
    val name: String
) : Parcelable
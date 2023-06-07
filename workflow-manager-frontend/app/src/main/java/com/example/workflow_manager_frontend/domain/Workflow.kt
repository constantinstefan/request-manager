package com.example.workflow_manager_frontend.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Workflow(
    var description: String = "",
    @PrimaryKey val id: Int? = 0,
    var name: String = "",
    val sharing: Sharing? = null
) : Parcelable
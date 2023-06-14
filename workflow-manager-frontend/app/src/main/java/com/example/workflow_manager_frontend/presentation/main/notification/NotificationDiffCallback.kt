package com.example.workflow_manager_frontend.presentation.main.notification

import androidx.recyclerview.widget.DiffUtil
import com.example.workflow_manager_frontend.domain.Notification
import com.example.workflow_manager_frontend.domain.Workflow

class NotificationDiffCallback : DiffUtil.ItemCallback<Notification>() {
    override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem == newItem
    }
}
package com.example.workflow_manager_frontend.presentation.main.home

import androidx.recyclerview.widget.DiffUtil
import com.example.workflow_manager_frontend.domain.Workflow

class WorkflowDiffCallback : DiffUtil.ItemCallback<Workflow>() {
    override fun areItemsTheSame(oldItem: Workflow, newItem: Workflow): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Workflow, newItem: Workflow): Boolean {
        return oldItem == newItem
    }
}
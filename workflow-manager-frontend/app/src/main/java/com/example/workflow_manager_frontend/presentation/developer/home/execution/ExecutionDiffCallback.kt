package com.example.workflow_manager_frontend.presentation.developer.home.execution

import androidx.recyclerview.widget.DiffUtil
import com.example.workflow_manager_frontend.domain.Execution

class ExecutionDiffCallback: DiffUtil.ItemCallback<Execution>() {
    override fun areItemsTheSame(oldItem: Execution, newItem: Execution): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Execution, newItem: Execution): Boolean {
        return oldItem == newItem
    }
}
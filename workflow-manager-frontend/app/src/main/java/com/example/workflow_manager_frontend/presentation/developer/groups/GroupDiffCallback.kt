package com.example.workflow_manager_frontend.presentation.developer.groups

import androidx.recyclerview.widget.DiffUtil
import com.example.workflow_manager_frontend.domain.Group
import com.example.workflow_manager_frontend.domain.Workflow

class GroupDiffCallback :DiffUtil.ItemCallback<Group>() {
    override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
        return oldItem == newItem
    }
}
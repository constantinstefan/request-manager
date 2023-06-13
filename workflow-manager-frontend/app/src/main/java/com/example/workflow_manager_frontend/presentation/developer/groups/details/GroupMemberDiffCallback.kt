package com.example.workflow_manager_frontend.presentation.developer.groups.details

import androidx.recyclerview.widget.DiffUtil
import com.example.workflow_manager_frontend.domain.Customer
import com.example.workflow_manager_frontend.domain.Group

class GroupMemberDiffCallback: DiffUtil.ItemCallback<Customer>(){
    override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
        return oldItem == newItem
    }
}
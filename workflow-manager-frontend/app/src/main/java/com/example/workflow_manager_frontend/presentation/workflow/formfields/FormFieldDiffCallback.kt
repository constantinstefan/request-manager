package com.example.workflow_manager_frontend.presentation.workflow.formfields

import androidx.recyclerview.widget.DiffUtil
import com.example.workflow_manager_frontend.domain.FormField


class FormFieldDiffCallback : DiffUtil.ItemCallback<FormField>() {
    override fun areItemsTheSame(oldItem: FormField, newItem: FormField): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FormField, newItem: FormField): Boolean {
        return oldItem == newItem
    }
}
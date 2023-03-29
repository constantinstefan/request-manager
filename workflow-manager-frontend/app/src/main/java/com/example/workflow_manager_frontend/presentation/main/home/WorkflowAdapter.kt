package com.example.workflow_manager_frontend.presentation.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.model.Workflow

class WorkflowAdapter(
    workflowDiffCallback: WorkflowDiffCallback
) : ListAdapter<Workflow, WorkflowAdapter.WorkflowViewHolder>(
        workflowDiffCallback) {

    class WorkflowViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val type: TextView

        init {
            title = itemView.findViewById(R.id.item_workflow_title)
            type = itemView.findViewById(R.id.item_workflow_type)
            // Define click listener for the ViewHolder's View title = itemView.findViewById(R.id.item_workflow_title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkflowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workflow,parent,false)
        return WorkflowViewHolder(view)
    }


    override fun onBindViewHolder(holder: WorkflowViewHolder, position: Int) {
        val workflow = getItem(position)
        holder.title.text = workflow.title
        holder.type.text = workflow.type
    }
}
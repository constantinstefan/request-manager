package com.example.workflow_manager_frontend.presentation.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.Workflow
import com.google.android.material.card.MaterialCardView

class WorkflowAdapter(
    workflowDiffCallback: WorkflowDiffCallback,
    private val onItemViewClicked: (Workflow) -> Unit
) : ListAdapter<Workflow, WorkflowAdapter.WorkflowViewHolder>(
        workflowDiffCallback) {

    inner class WorkflowViewHolder(
        itemView : View,
        private val onItemViewClicked : (Workflow)-> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val type: TextView
        val card: MaterialCardView
        //val group: TextView

        init {
            title = itemView.findViewById(R.id.item_workflow_title)
            type = itemView.findViewById(R.id.item_workflow_type)
            card = itemView.findViewById(R.id.item_workflow_card)
            //group = itemView.findViewById(R.id.item_workflow_group)

            card.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val workflow = getItem(position)
                    onItemViewClicked(workflow)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkflowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workflow,parent,false)
        return WorkflowViewHolder(view, onItemViewClicked)
    }


    override fun onBindViewHolder(holder: WorkflowViewHolder, position: Int) {
        val workflow = getItem(position)
        holder.title.text = workflow.name
        holder.type.text = if(workflow.sharing?.sharingType == "PUBLIC") "PUBLIC"
            else workflow.sharing?.group?.name
    }
}
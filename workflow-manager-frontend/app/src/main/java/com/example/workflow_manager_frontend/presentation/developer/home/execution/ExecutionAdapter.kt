package com.example.workflow_manager_frontend.presentation.developer.home.execution

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.Execution

class ExecutionAdapter(
    executionDiffCallback: ExecutionDiffCallback,
    private val onItemViewClicked: (Execution) -> Unit
) : ListAdapter<Execution, ExecutionAdapter.ExecutionViewHolder>(executionDiffCallback) {

    inner class ExecutionViewHolder (
        itemView : View,
        private val onItemViewClicked : (Execution)-> Unit
    ) : RecyclerView.ViewHolder(itemView){

        val title: TextView

        init {
            title = itemView.findViewById(R.id.item_execution_title)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExecutionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_execution,parent,false)
        return ExecutionViewHolder(view, onItemViewClicked)
    }

    override fun onBindViewHolder(holder: ExecutionViewHolder, position: Int) {
        val execution = getItem(position)
        holder.title.text = "Execution #${execution.id}"
    }
}
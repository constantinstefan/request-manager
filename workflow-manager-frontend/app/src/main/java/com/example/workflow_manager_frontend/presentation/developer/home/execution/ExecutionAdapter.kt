package com.example.workflow_manager_frontend.presentation.developer.home.execution

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.Execution
import com.google.android.material.card.MaterialCardView

class ExecutionAdapter(
    private val context: Context,
    executionDiffCallback: ExecutionDiffCallback,
    private val onItemViewClicked: (Execution) -> Unit
) : ListAdapter<Execution, ExecutionAdapter.ExecutionViewHolder>(executionDiffCallback) {

    inner class ExecutionViewHolder (
        itemView : View,
    ) : RecyclerView.ViewHolder(itemView){

        val title: TextView
        val icon: ImageView
        val number: TextView
        val card: MaterialCardView

        init {
            title = itemView.findViewById(R.id.item_execution_title)
            number = itemView.findViewById(R.id.item_execution_number)
            icon = itemView.findViewById(R.id.imageView)
            card = itemView.findViewById(R.id.item_card)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExecutionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_execution,parent,false)
        return ExecutionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExecutionViewHolder, position: Int) {
        val execution = getItem(position)
        holder.title.text = "Execution"
        holder.number.text = "#${execution.id}"
        ViewUtility.updateIcon(holder.icon, context, execution)

        holder.card.setOnClickListener {
            onItemViewClicked(execution)
        }
    }
}
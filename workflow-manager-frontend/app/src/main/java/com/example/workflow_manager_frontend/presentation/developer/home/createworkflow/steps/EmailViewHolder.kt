package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.steps

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.WorkflowStep

class EmailViewHolder(
    itemView: View,
    private val context: Context,
    private val deleteStepListener: DeleteStepListener
) : RecyclerView.ViewHolder(itemView), StepsViewHolder {
    private val tag = "EmailViewHolder"

    private val options = itemView.findViewById<ImageView>(R.id.optionsIcon)

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun bind(item: WorkflowStep) {
        val onDeleteAction: () -> Unit = {
            deleteStepListener.onDeleteStep(bindingAdapterPosition)
        }
        OptionsMenuHelper.setupOptionsMenu(context, options, onDeleteAction)
    }
}
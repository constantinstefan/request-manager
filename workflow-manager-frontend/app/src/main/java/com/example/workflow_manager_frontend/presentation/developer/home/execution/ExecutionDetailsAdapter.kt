package com.example.workflow_manager_frontend.presentation.developer.home.execution

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.Execution
import com.example.workflow_manager_frontend.domain.StepExecution
import com.example.workflow_manager_frontend.domain.WorkflowStep

class ExecutionDetailsAdapter(
    private val context: Context,
    executionDetailsDiffCallback: ExecutionDetailsDiffCallback
) : ListAdapter<StepExecution, ExecutionDetailsAdapter.ExecutionDetailsViewHolder>(executionDetailsDiffCallback){

    private val tag = "ExecutionDetailsAdapter"

    inner class ExecutionDetailsViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView){

        val circleView: View
        val numberText: TextView
        val stepName: TextView
        val failureMessage: TextView

        init {
            circleView = itemView.findViewById(R.id.circleView)
            numberText = itemView.findViewById(R.id.numberText)
            stepName = itemView.findViewById(R.id.stepName)
            failureMessage = itemView.findViewById(R.id.failureMessage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExecutionDetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_execution_details,parent,false)
        return ExecutionDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExecutionDetailsViewHolder, position: Int) {
        Log.d(tag, "cosmin bind $position")

        val stepExecution = getItem(position)
        holder.numberText.text = stepExecution.step.stepNumber.toString()
        holder.stepName.text = stepExecution.step.stepType

        updateColor(position, holder, stepExecution)

        val currentExecutionPosition = stepExecution.execution.stepNumber - 1
        if( (currentExecutionPosition == position) && (stepExecution.execution.status == "FAILURE") ) {
            holder.failureMessage.visibility = View.VISIBLE
            holder.failureMessage.text = stepExecution.execution.message
        }
    }

    private fun updateColor(position: Int, holder: ExecutionDetailsViewHolder, stepExecution: StepExecution) {
        val currentExecutionPosition = stepExecution.execution.stepNumber - 1
        Log.d("executionDetailsAdapter","position $position currentExecutionPosition $currentExecutionPosition")

        if(position < currentExecutionPosition) {
            holder.circleView.background.setTint(ContextCompat.getColor(context, R.color.green_secondary))
            return;
        }
        if(position == currentExecutionPosition) {
            when (stepExecution.execution.status) {
                "SUCCESS" -> holder.circleView.background.setTint(
                    ContextCompat.getColor(context, R.color.green_secondary))
                "FAILURE" -> holder.circleView.background.setTint(
                    ContextCompat.getColor(context,R.color.red_error))
                "IN_PROGRESS" -> holder.circleView.background.setTint(
                    ContextCompat.getColor(context, R.color.purple_primary))
                else -> Log.e("execution", "wrong status ${stepExecution.execution.status}")
            }
            return;
        }
        holder.circleView.background.setTint(ContextCompat.getColor(context, androidx.appcompat.R.color.material_grey_600))
    }
}
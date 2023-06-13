package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.steps

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.WorkflowStep
import com.google.android.material.textfield.TextInputLayout

class ChatGptPromptViewHolder(
    itemView: View,
    private val context: Context,
    private val deleteStepListener: DeleteStepListener,
    private val viewModel: StepsViewModel
) : RecyclerView.ViewHolder(itemView), StepsViewHolder {

    private val options = itemView.findViewById<ImageView>(R.id.optionsIcon)
    private val outputVariableTextField = itemView.findViewById<TextInputLayout>(R.id.outputVariableTextField)
    private val promptTextField = itemView.findViewById<TextInputLayout>(R.id.promptTextField)

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun bind(item: WorkflowStep) {
        outputVariableTextField.editText?.setText(item.chatGptStep?.outputVariable)
        promptTextField.editText?.setText(item.chatGptStep?.prompt)

        outputVariableTextField.editText?.addTextChangedListener {
            viewModel.setOutputVariableForChatGptStep(bindingAdapterPosition, it.toString())
        }
        promptTextField.editText?.addTextChangedListener {
            viewModel.setPromptForChatGptStep(bindingAdapterPosition, it.toString())
        }

        val onDeleteAction: () -> Unit = {
            deleteStepListener.onDeleteStep(bindingAdapterPosition)
        }
        OptionsMenuHelper.setupOptionsMenu(context, options, onDeleteAction)
    }
}
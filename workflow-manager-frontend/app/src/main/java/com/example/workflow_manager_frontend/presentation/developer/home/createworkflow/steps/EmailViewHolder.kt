package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.steps

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.WorkflowStep
import com.google.android.material.textfield.TextInputLayout

class EmailViewHolder(
    itemView: View,
    private val context: Context,
    private val deleteStepListener: DeleteStepListener,
    private val viewModel: StepsViewModel
) : RecyclerView.ViewHolder(itemView), StepsViewHolder {
    private val tag = "EmailViewHolder"

    private val options = itemView.findViewById<ImageView>(R.id.optionsIcon)

    private val receiverTextInputLayout = itemView.findViewById<TextInputLayout>(R.id.receiverTextField)
    private val subjectTextInputLayout = itemView.findViewById<TextInputLayout>(R.id.subjectTextField)
    private val contentTextInputLayout = itemView.findViewById<TextInputLayout>(R.id.contentTextField)
    private val attachmentsTextInputLayout = itemView.findViewById<TextInputLayout>(R.id.attachmentsTextField)

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun bind(item: WorkflowStep) {
        receiverTextInputLayout.editText?.setText(item.email?.receiverEmail)
        subjectTextInputLayout.editText?.setText(item.email?.subject)
        contentTextInputLayout.editText?.setText(item.email?.content)
        attachmentsTextInputLayout.editText?.setText(item.email?.attachments)

        receiverTextInputLayout.editText?.addTextChangedListener {
            viewModel.setReceiverForEmail(bindingAdapterPosition, it.toString())
        }
        subjectTextInputLayout.editText?.addTextChangedListener {
            viewModel.setSubjectForEmail(bindingAdapterPosition, it.toString())
        }
        contentTextInputLayout.editText?.addTextChangedListener {
            viewModel.setContentForEmail(bindingAdapterPosition, it.toString())
        }
        attachmentsTextInputLayout.editText?.addTextChangedListener {
            viewModel.setAttachmentsForEmail(bindingAdapterPosition, it.toString())
        }

        val onDeleteAction: () -> Unit = {
            deleteStepListener.onDeleteStep(bindingAdapterPosition)
        }
        OptionsMenuHelper.setupOptionsMenu(context, options, onDeleteAction)
    }
}
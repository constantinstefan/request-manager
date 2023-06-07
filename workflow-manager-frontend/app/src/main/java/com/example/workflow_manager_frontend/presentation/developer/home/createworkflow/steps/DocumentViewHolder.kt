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
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout

class DocumentViewHolder(
    itemView: View,
    private val context: Context,
    private val deleteStepListener: DeleteStepListener,
    private val viewModel: StepsViewModel
) : RecyclerView.ViewHolder(itemView), StepsViewHolder {

    private val tag = "DocumentViewHolder"

    private val options = itemView.findViewById<ImageView>(R.id.optionsIcon)
    private val descriptionTextField = itemView.findViewById<TextInputLayout>(R.id.descriptionTextField)
    private val fileContentVariableTextField = itemView.findViewById<TextInputLayout>(R.id.fileContentVariable)
    private val isRequiredSwitch = itemView.findViewById<SwitchMaterial>(R.id.requiredSwitch)


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun bind(item: WorkflowStep) {

        if(item.document?.description?.isNotEmpty() == true) descriptionTextField.editText?.setText(item.document.description)
        if(item.document?.uploadedFileVariable?.isNotEmpty() == true) fileContentVariableTextField.editText?.setText(item.document.description)
        isRequiredSwitch.isChecked = item.document?.isRequired ?: false

        descriptionTextField.editText?.addTextChangedListener {
            viewModel.setDocumentDescription(bindingAdapterPosition, it.toString())
        }
        fileContentVariableTextField.editText?.addTextChangedListener {
            viewModel.setDocumentUploadFileContentVariable(bindingAdapterPosition, it.toString())
        }
        isRequiredSwitch.setOnCheckedChangeListener { _, isChecked  ->
            viewModel.setIsRequiredForDocument(bindingAdapterPosition, isChecked)
        }

        val onDeleteAction: () -> Unit = {
            deleteStepListener.onDeleteStep(bindingAdapterPosition)
        }
        OptionsMenuHelper.setupOptionsMenu(context, options, onDeleteAction)
    }
}
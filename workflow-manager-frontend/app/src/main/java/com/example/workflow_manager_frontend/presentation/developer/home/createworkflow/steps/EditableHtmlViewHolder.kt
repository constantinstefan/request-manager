package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.steps

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.WorkflowStep
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout

class EditableHtmlViewHolder(
    itemView: View,
    private val context: Context,
    private val deleteStepListener: DeleteStepListener,
    private val htmlUploadListener: HtmlUploadListener,
    private val viewModel: StepsViewModel
) : RecyclerView.ViewHolder(itemView), StepsViewHolder {

    private val tag = "EditableHtmlViewHolder"

    private val options = itemView.findViewById<ImageView>(R.id.optionsIcon)
    private val uploadIcon: ImageView = itemView.findViewById(R.id.uploadIcon)
    private val uploadedFileTextView = itemView.findViewById<TextView>(R.id.uploadedFileTextView)
    private val htmlTextInputLayout = itemView.findViewById<TextInputLayout>(R.id.htmlTextInputLayout)
    private val pdfTextInputLayout = itemView.findViewById<TextInputLayout>(R.id.pdfTextInputLayout)
    private val isRequiredSwitch = itemView.findViewById<SwitchMaterial>(R.id.requiredSwitch)

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun bind(item: WorkflowStep) {
        htmlTextInputLayout.editText?.addTextChangedListener {
            viewModel.setHtmlFileVariableForEditableHtml(bindingAdapterPosition, it.toString())
        }
        pdfTextInputLayout.editText?.addTextChangedListener {
            viewModel.setPdfFileVariableForEditableHtml(bindingAdapterPosition, it.toString())
        }
        isRequiredSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setIsRequiredForEditableHtml(bindingAdapterPosition, isChecked)
        }

        val onDeleteAction: () -> Unit = {
            deleteStepListener.onDeleteStep(bindingAdapterPosition)
        }
        OptionsMenuHelper.setupOptionsMenu(context,options, onDeleteAction)

        uploadedFileTextView.text = item.editableHtml?.fileName?.ifBlank{"No file uploaded"} as CharSequence
        uploadIcon.setOnClickListener {
            htmlUploadListener.onHtmlUpload(bindingAdapterPosition)
        }
    }
}
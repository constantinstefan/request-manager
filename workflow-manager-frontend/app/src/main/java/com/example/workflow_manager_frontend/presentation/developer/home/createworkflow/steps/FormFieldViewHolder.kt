package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.steps

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.FormField
import com.example.workflow_manager_frontend.domain.WorkflowStep
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormFieldViewHolder(
    itemView: View,
    private val context: Context,
    private val deleteStepListener: DeleteStepListener
) : RecyclerView.ViewHolder(itemView), StepsViewHolder {

    private val tag = "FormFieldsViewHolder"

    private val fieldsContainer: LinearLayout = itemView.findViewById(R.id.fieldsContainer)
    private val addFieldIcon: ImageView = itemView.findViewById(R.id.addFieldIcon)
    private val options = itemView.findViewById<ImageView>(R.id.optionsIcon)


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun bind(item: WorkflowStep) {
        val onDeleteAction: () -> Unit = {
            deleteStepListener.onDeleteStep(bindingAdapterPosition)
        }
        OptionsMenuHelper.setupOptionsMenu(context, options, onDeleteAction)

        fieldsContainer.removeAllViews()

        for (formField in item.formFields!!) {
            val formFieldView = LayoutInflater.from(itemView.context).inflate(R.layout.item_add_single_form_field, fieldsContainer, false)
            bindFormField(formFieldView, formField, item)
            fieldsContainer.addView(formFieldView)
        }

        addFieldIcon.setOnClickListener {
            val newFormField = FormField()
            item.formFields.add(newFormField)

            val formFieldView = LayoutInflater.from(itemView.context).inflate(R.layout.item_add_single_form_field, fieldsContainer, false)
            bindFormField(formFieldView, newFormField, item)
            fieldsContainer.addView(formFieldView)
        }
    }

    fun bindFormField(formFieldView: View, formField: FormField, item: WorkflowStep) {
        val fieldNameTextField = formFieldView.findViewById<TextInputLayout>(R.id.nameTextField)
        val fieldLabelTextField = formFieldView.findViewById<TextInputLayout>(R.id.labelTextField)
        val fieldRequiredSwitch = formFieldView.findViewById<Switch>(R.id.requiredSwitch)
        val deleteFieldIcon = formFieldView.findViewById<ImageView>(R.id.deleteFieldIcon)

        //fieldNameTextField.editText?.setText("")
        fieldLabelTextField.editText?.setText(formField.label)
        fieldRequiredSwitch.isChecked = false

        deleteFieldIcon.setOnClickListener {
            item.formFields?.remove(formField)
            fieldsContainer.removeView(formFieldView)
        }
    }
}
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
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.FormField
import com.example.workflow_manager_frontend.domain.WorkflowStep
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormFieldViewHolder(
    itemView: View,
    private val context: Context,
    private val deleteStepListener: DeleteStepListener,
    private val viewModel: StepsViewModel
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

        val existingCount = fieldsContainer.childCount
        val newCount = item.formFields?.size ?: 1

        if(newCount < existingCount) {
            fieldsContainer.removeViews(newCount, existingCount - newCount)
        }


        //fieldsContainer.removeAllViews()
        // if remove all views and bind again with values from viewmodel
        // a flickering is happening when adding a new step in the recyclerview
        for (formFieldIndex in 0 until newCount) {
            val shouldAddView = (formFieldIndex > existingCount) || (existingCount == 0)

            val formFieldView = if(shouldAddView) LayoutInflater.from(itemView.context).inflate(R.layout.item_add_single_form_field, fieldsContainer, false)
                else fieldsContainer.getChildAt(formFieldIndex)
            val formField = item.formFields?.get(formFieldIndex)

            if(shouldAddView && (formField != null) ){
                bindFormField(formFieldView, formField, item)
                fieldsContainer.addView(formFieldView)
            }
        }

        addFieldIcon.setOnClickListener {
            val newFormField = FormField()
            item.formFields?.add(newFormField)

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

        fieldNameTextField.editText?.setText(formField.name)
        fieldLabelTextField.editText?.setText(formField.label)
        fieldRequiredSwitch.isChecked = formField.isRequired

        fieldLabelTextField.editText?.addTextChangedListener {
            item.formFields?.indexOf(formField)?.let { stepPosition ->
                viewModel.setLabelForFormField(bindingAdapterPosition, stepPosition, it.toString()) }
        }
        fieldNameTextField.editText?.addTextChangedListener {
            item.formFields?.indexOf(formField)?.let { stepPosition ->
                viewModel.setNameForFormField(bindingAdapterPosition, stepPosition, it.toString()) }
        }
        fieldRequiredSwitch.setOnCheckedChangeListener { _, isChecked ->
            item.formFields?.indexOf(formField)?.let { stepPosition ->
                viewModel.setIsRequiredForFormField(bindingAdapterPosition, stepPosition, isChecked) }
        }

        deleteFieldIcon.setOnClickListener {
            item.formFields?.remove(formField)
            fieldsContainer.removeView(formFieldView)
        }
    }
}
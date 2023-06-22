package com.example.workflow_manager_frontend.presentation.main.home.workflow.formfields

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.FormField
import com.example.workflow_manager_frontend.domain.WorkflowExecutionContext
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class FormFieldAdapter(
    formFieldDiffCallback: FormFieldDiffCallback
) : ListAdapter<FormField, FormFieldAdapter.FormFieldViewHolder>(formFieldDiffCallback),
    SubmitFormListener {

    private val viewHolders: MutableList<FormFieldViewHolder> = mutableListOf()

    inner class FormFieldViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView){
        val name: TextInputEditText
        val layout : TextInputLayout

        init{
            name = itemView.findViewById(R.id.field)
            layout = itemView.findViewById(R.id.layout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormFieldViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_form_field, parent,false)
        return FormFieldViewHolder(view)
    }

    override fun onBindViewHolder(holder: FormFieldViewHolder, position: Int) {
        val formField = getItem(position)
        viewHolders.add(holder)
        holder.name.hint = formField.name

        holder.name.hint = if(formField.isRequired) "${formField.name}*"
            else formField.name
    }

    fun getEditedText(position: Int?) : String {
        if(position == null) return ""
        return viewHolders[position].name.text.toString()
    }

    override fun onFieldError(position: Int, errorMessage: String) {
        viewHolders[position].layout.error = errorMessage
    }

    override fun onSubmitForm(workflowExecutionContext: WorkflowExecutionContext) {
        for (position in 0 until itemCount) {
            workflowExecutionContext.variableMap[getItem(position).label] = getEditedText(position)
        }
    }
}
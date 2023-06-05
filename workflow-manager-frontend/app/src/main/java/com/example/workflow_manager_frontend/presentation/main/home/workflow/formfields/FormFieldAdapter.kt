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


class FormFieldAdapter(
    formFieldDiffCallback: FormFieldDiffCallback
) : ListAdapter<FormField, FormFieldAdapter.FormFieldViewHolder>(formFieldDiffCallback),
    SubmitFormListener {

    private val viewHolders: MutableList<FormFieldViewHolder> = mutableListOf()

    inner class FormFieldViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView){
        val field: TextInputEditText

        init{
            field = itemView.findViewById(R.id.field)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormFieldViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_form_field, parent,false)
        return FormFieldViewHolder(view)
    }

    override fun onBindViewHolder(holder: FormFieldViewHolder, position: Int) {
        val formField = getItem(position)
        viewHolders.add(holder)
        holder.field.hint = formField.label
    }

    private fun getEditedText(position: Int) : String {
        return viewHolders.get(position).field.text.toString()
    }

    override fun onSubmitForm(workflowExecutionContext: WorkflowExecutionContext) {
        for (position in 0 until itemCount) {
            workflowExecutionContext.variableMap[getItem(position).label] = getEditedText(position)
        }
    }
}
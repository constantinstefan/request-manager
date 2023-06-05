package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.steps

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.WorkflowStep

class WorkflowStepsAdapter(
    private val fragment: StepsFragment,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val tag = "WorkflowStepAdapter"

    private var items: MutableList<WorkflowStep> = mutableListOf()

    fun setItems(items: MutableList<WorkflowStep>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DOCUMENT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_document, parent, false)
                DocumentViewHolder(view, parent.context, fragment as DeleteStepListener)
            }
            VIEW_TYPE_FORM_FIELD -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_form_field, parent, false)
                FormFieldViewHolder(view, parent.context, fragment as DeleteStepListener)
            }
            VIEW_TYPE_EDITABLE_HTML -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_editable_html, parent, false)
                EditableHtmlViewHolder(view, parent.context, fragment as DeleteStepListener, fragment as HtmlUploadListener)
            }
            VIEW_TYPE_EMAIL -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_email, parent, false)
                EmailViewHolder(view, parent.context, fragment as DeleteStepListener)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as? StepsViewHolder)?.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
            val item = items[position]
            return when (item.stepType) {
                "DOCUMENT" -> VIEW_TYPE_DOCUMENT
                "FORM_FIELD" -> VIEW_TYPE_FORM_FIELD
                "EDITABLE_HTML" -> VIEW_TYPE_EDITABLE_HTML
                "EMAIL" -> VIEW_TYPE_EMAIL
                else -> throw IllegalArgumentException("Invalid item type")
            }
    }

    companion object {
        const val VIEW_TYPE_DOCUMENT = 0
        const val VIEW_TYPE_FORM_FIELD = 1
        const val VIEW_TYPE_EDITABLE_HTML = 2
        const val VIEW_TYPE_EMAIL = 3
    }

}
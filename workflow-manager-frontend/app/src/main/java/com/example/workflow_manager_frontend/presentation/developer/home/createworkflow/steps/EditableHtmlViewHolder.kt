package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.steps

import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.Document
import com.example.workflow_manager_frontend.domain.EditableHtml
import com.example.workflow_manager_frontend.domain.Email
import com.example.workflow_manager_frontend.domain.WorkflowStep

class EditableHtmlViewHolder(
    itemView: View,
    private val context: Context,
    private val deleteStepListener: DeleteStepListener,
    private val htmlUploadListener: HtmlUploadListener
) : RecyclerView.ViewHolder(itemView), StepsViewHolder {

    private val tag = "EditableHtmlViewHolder"

    private val options = itemView.findViewById<ImageView>(R.id.optionsIcon)
    private val uploadIcon: ImageView = itemView.findViewById(R.id.uploadIcon)
    private val uploadedFileTextView = itemView.findViewById<TextView>(R.id.uploadedFileTextView)


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun bind(item: WorkflowStep) {
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
package com.example.workflow_manager_frontend.domain.request

data class AddEditableHtmlStepRequest(
    val isRequired: Boolean,
    val pdfResultVariable: String,
    val uploadedEditedHtmlFileVariable: String
)
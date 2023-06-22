package com.example.workflow_manager_frontend.presentation.main.home.workflow.formfields

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workflow_manager_frontend.domain.FormField

class FormFieldsViewModel : ViewModel() {

    val fieldError = MutableLiveData<String?>()

    fun validateField(text : String) : String {
        return if(text.isEmpty()) "Cannot be empty!"
            else ""
    }
}

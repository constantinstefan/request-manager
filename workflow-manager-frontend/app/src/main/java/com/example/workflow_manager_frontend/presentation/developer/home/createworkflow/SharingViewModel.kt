package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workflow_manager_frontend.domain.Sharing
import com.example.workflow_manager_frontend.domain.Workflow

class SharingViewModel : ViewModel() {

    private val sharing: MutableLiveData<Sharing?> = MutableLiveData()


    fun setType(sharingType: String) {
        val currentSharing: Sharing = sharing.value ?: Sharing()
        currentSharing.sharingType = sharingType
        sharing.value = currentSharing
    }

    fun getType() : String? {
        return sharing.value?.sharingType
    }
}
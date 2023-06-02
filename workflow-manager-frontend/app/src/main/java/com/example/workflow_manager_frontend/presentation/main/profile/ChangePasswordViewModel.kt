package com.example.workflow_manager_frontend.presentation.main.profile

import androidx.lifecycle.ViewModel
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
import com.example.workflow_manager_frontend.domain.request.ChangePasswordRequest

class ChangePasswordViewModel : ViewModel() {

    suspend fun changePassword(oldPassword: String, newPassword: String) :Boolean {

        val principalResponse = RetrofitInstance.api.getPrincipal()
        if (!principalResponse.isSuccessful) {
            return false
        }

        val changePasswordResponse = principalResponse.body()?.let { principal ->
            RetrofitInstance.api.patchCustomerPassword(principal.id, ChangePasswordRequest(oldPassword, newPassword))
        }

        return changePasswordResponse?.isSuccessful ?: false
    }
}
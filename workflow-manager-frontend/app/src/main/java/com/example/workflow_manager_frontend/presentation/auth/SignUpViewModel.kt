package com.example.workflow_manager_frontend.presentation.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
import com.example.workflow_manager_frontend.domain.Customer


class SignUpViewModel : ViewModel() {

    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()

    fun validateFields(email: String,
                       password: String,
                       confirmPassword: String) : Boolean{
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")

        if (email.isEmpty()) {
            emailError.value = "Email cannot be empty"
        } else if (!email.matches(emailPattern)) {
            emailError.value = "Invalid email format"
        }
        else {
            emailError.value = null
        }

        if (password.isEmpty()) {
            passwordError.value = "Password cannot be empty"
        } else if (confirmPassword.isEmpty()) {
            passwordError.value = "Confirm password cannot be empty"
        } else if (password != confirmPassword) {
            passwordError.value = "Passwords do not match"
        }
        else {
            passwordError.value = null
        }

        return emailError.value == null
                && passwordError.value == null
    }
    
    suspend fun signup(email: String, password: String, isDeveloper: Boolean) : Boolean {
        val response  = RetrofitInstance.api.postCustomers(Customer(email,null, 0,null,
            if (isDeveloper) "ROLE_DEVELOPER" else "ROLE_CUSTOMER", password))
        if(! response.isSuccessful) {
            Log.e("SignUpViewModel", response.toString())
            return false
        }
        return true
    }
}
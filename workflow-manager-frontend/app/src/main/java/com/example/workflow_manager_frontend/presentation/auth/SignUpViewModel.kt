package com.example.workflow_manager_frontend.presentation.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
import com.example.workflow_manager_frontend.domain.Customer
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


class SignUpViewModel : ViewModel() {

    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()
    val confirmPasswordError = MutableLiveData<String?>()

    val email = MutableLiveData<String?>()
    val password = MutableLiveData<String?>()
    val confirmPassword = MutableLiveData<String?>()
    val isDeveloper = MutableLiveData(false)

    val success = MutableLiveData(false)

    fun validateEmail(email: String?) :Boolean {
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")

        if (email == null || email.isEmpty()) {
            emailError.value = "Email cannot be empty"
        } else if (!email.matches(emailPattern)) {
            emailError.value = "Invalid email format"
        }
        else {
            emailError.value = null
        }
        return emailError.value == null
    }

    fun validatePassword(password: String?) : Boolean {
        if(password == null || password.isEmpty()) {
            passwordError.value = "Password cannot be empty"
        }
        else if (! password.matches(Regex("^(?=.*[^a-zA-Z0-9]).{8,}$"))) {
            passwordError.value = "Password must have at least 8 characters and at least one special character"
        }
        else {
            passwordError.value = null
        }
        return passwordError.value == null
    }

    fun validateConfirmPassword(password: String?, confirmPassword: String?) : Boolean {
        if(password == null || password.isEmpty()) {
            confirmPasswordError.value = null
            return true
        }

        if(confirmPassword == null || confirmPassword.isEmpty()) {
            confirmPasswordError.value = "Confirm password cannot be empty"
        }
        else if(password != confirmPassword) {
            confirmPasswordError.value = "Passwords do not match"
        }
        else {
            confirmPasswordError.value = null
        }
        return confirmPasswordError.value == null
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

     fun handleSignUp() {
         viewModelScope.launch {
             val isEmailValid = validateEmail(email.value)
             val isPasswordValid = validatePassword(password.value)
             val isConfirmPasswordValid = validateConfirmPassword(password.value, confirmPassword.value)

             if(!isEmailValid || !isPasswordValid || !isConfirmPasswordValid){
                 return@launch
             }

             if(signup(email.value!!, password.value!!, isDeveloper.value!!)) {
                 success.postValue(true)
             }
         }
    }

}
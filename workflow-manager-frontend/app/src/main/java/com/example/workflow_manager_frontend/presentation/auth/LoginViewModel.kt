package com.example.workflow_manager_frontend.presentation.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workflow_manager_frontend.data.repository.JwtRepository
import com.example.workflow_manager_frontend.data.repository.UserRepository
import com.example.workflow_manager_frontend.data.source.db.JwtDao
import com.example.workflow_manager_frontend.domain.request.AuthenticationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val jwtRepository: JwtRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val role : MutableLiveData<String?> = MutableLiveData()
    val email : MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    suspend fun login(email: String, password: String) : Boolean {
        var isOk = false

        val jwt = jwtRepository.getJwtFromRemote(AuthenticationRequest(email, password))
        if (jwt != null) {
            withContext(Dispatchers.IO) {
                jwtRepository.insertJwt(jwt)
            }
            isOk = true
        }

        return isOk
    }

    fun onLoginButtonClick() {
        viewModelScope.launch {
            if(login(email.value!!, password.value!!)) {
                getRole()
            }
        }
    }

    suspend fun getRole() {
        val principal = userRepository.getPrincipal()
        role.postValue(principal?.role)
    }
}
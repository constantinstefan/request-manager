package com.example.workflow_manager_frontend.presentation.auth

import androidx.lifecycle.ViewModel
import com.example.workflow_manager_frontend.data.repository.JwtRepository
import com.example.workflow_manager_frontend.data.source.db.JwtDao
import com.example.workflow_manager_frontend.domain.request.AuthenticationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val jwtRepository: JwtRepository
) : ViewModel() {

    suspend fun login(email: String, password: String) : Boolean {
        var isOk = false

        val jwt = jwtRepository.getJwtFromRemote(AuthenticationRequest(email, password))
        if (jwt != null) {
            jwtRepository.insertJwt(jwt)
            isOk = true
        }

        return isOk
    }
}
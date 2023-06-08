package com.example.workflow_manager_frontend.presentation.main.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workflow_manager_frontend.data.repository.JwtRepository
import com.example.workflow_manager_frontend.data.repository.UserRepository
import com.example.workflow_manager_frontend.domain.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val jwtRepository: JwtRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val state: MutableLiveData<Customer?> = MutableLiveData()

    init {
        viewModelScope.launch {
            state.value = userRepository.getPrincipal()
        }
    }

    fun getState(): MutableLiveData<Customer?> {
        return state
    }

    suspend fun logout() {
        jwtRepository.deleteJwt()
    }
}
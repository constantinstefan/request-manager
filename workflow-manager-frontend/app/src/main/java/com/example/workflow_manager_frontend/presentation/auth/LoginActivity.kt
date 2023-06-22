package com.example.workflow_manager_frontend.presentation.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
import com.example.workflow_manager_frontend.databinding.ActivityLoginBinding
import com.example.workflow_manager_frontend.domain.request.AuthenticationRequest
import com.example.workflow_manager_frontend.presentation.developer.DeveloperMainActivity
import com.example.workflow_manager_frontend.presentation.main.MainActivity
import com.example.workflow_manager_frontend.presentation.main.home.HomeViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val tag ="LoginActivity"

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this

        binding.signUpTextLink.setOnClickListener() {
            createSignUpActivityIntent()
        }

        loginViewModel.role.observe(this) {
            when (it) {
                "ROLE_CUSTOMER" -> createMainActivityIntent()
                "ROLE_DEVELOPER" -> createDeveloperMainActivityIntent()
                else -> Log.e(tag, "invalid role: $it")
            }
        }
    }
    private fun createMainActivityIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun createDeveloperMainActivityIntent() {
        val intent = Intent(this, DeveloperMainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun createSignUpActivityIntent() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }
}
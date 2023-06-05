package com.example.workflow_manager_frontend.presentation.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
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
        setContentView(R.layout.activity_login)

        val signUpTextLink : TextView = findViewById(R.id.signUpTextLink)
        signUpTextLink.setOnClickListener() {
            createSignUpActivityIntent()
        }

        val loginButton: Button = findViewById(R.id.loginButton)
        loginButton.setOnClickListener() {
            lifecycleScope.launch{
                    handleLogin()
            }
        }

    }

    private suspend fun handleLogin() {
        val userName = findViewById<TextInputEditText>(R.id.usernameField).text.toString()
        val password = findViewById<TextInputEditText>(R.id.passwordField).text.toString()

        withContext(Dispatchers.IO) {
            if (! loginViewModel.login(userName, password)) {
                return@withContext
            }

            when(val role = loginViewModel.getRole()) {
                "ROLE_CUSTOMER" -> createMainActivityIntent()
                "ROLE_DEVELOPER" -> createDeveloperMainActivityIntent()
                else -> Log.e(tag, "invalid role: $role")
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
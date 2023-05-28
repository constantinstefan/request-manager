package com.example.workflow_manager_frontend.presentation.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.lifecycleScope
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.presentation.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val signUpViewModel : SignUpViewModel by viewModels()

    private val tag : String = "SignUpActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val emailFieldLayout : TextInputLayout = findViewById(R.id.usernameFieldLayoutSignUp)
        val passwordFieldLayout: TextInputLayout = findViewById(R.id.passwordFieldLayoutSignUp)
        val confirmPasswordFieldLayout : TextInputLayout = findViewById(R.id.confirmPasswordFieldLayoutSignUp)
        val signUpButton : Button = findViewById(R.id.signupButton)
        val loginTextLink: TextView = findViewById(R.id.loginTextLinkOnSignUpPage)
        val accountTypeSwitch : SwitchMaterial = findViewById(R.id.accountTypeSwitch)

        signUpViewModel.emailError.observe(this) { errorMessage ->
            emailFieldLayout.error = errorMessage
        }

        signUpViewModel.passwordError.observe(this) { errorMessage ->
            passwordFieldLayout.error = errorMessage
            confirmPasswordFieldLayout.error = errorMessage
        }

        loginTextLink.setOnClickListener {
            createLoginIntent()
        }

        signUpButton.setOnClickListener {
            lifecycleScope.launch {
                handleSignUp(emailFieldLayout, passwordFieldLayout, confirmPasswordFieldLayout, accountTypeSwitch)
            }
        }
    }

    private suspend fun handleSignUp(emailFieldLayout : TextInputLayout,
                                     passwordFieldLayout: TextInputLayout,
                                     confirmPasswordFieldLayout: TextInputLayout,
                                     accountTypeSwitch: SwitchMaterial) {
        val email = emailFieldLayout.editText?.text.toString()
        val password = passwordFieldLayout.editText?.text.toString()
        val confirmPassword = confirmPasswordFieldLayout.editText?.text.toString()
        val isDeveloper = accountTypeSwitch.isChecked

        if (! signUpViewModel.validateFields(email, password, confirmPassword)) {
            return;
        }

        withContext(Dispatchers.IO) {
            if(signUpViewModel.signup(email, password, isDeveloper)) {
                Log.d(tag, "sign up successfully");
                runOnUiThread {
                    showSnackBar()
                }
                TimeUnit.SECONDS.sleep(1L);
                createLoginIntent()
            }
        }
    }

    private fun showSnackBar() {
        val signupLayout = findViewById<ConstraintLayout>(R.id.signupLayout)
        val snackbar = Snackbar.make(signupLayout, "Successful sign up!", Snackbar.LENGTH_LONG)
        //snackbar.anchorView = findViewById(R.id.logo)
        snackbar.show();
    }

    private fun createLoginIntent() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
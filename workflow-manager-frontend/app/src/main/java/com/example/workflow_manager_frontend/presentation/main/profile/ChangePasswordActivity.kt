package com.example.workflow_manager_frontend.presentation.main.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.example.workflow_manager_frontend.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class ChangePasswordActivity : AppCompatActivity() {

    private val changePasswordViewModel : ChangePasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val backButton : ImageView = findViewById(R.id.backButton)
        val changePasswordButton : Button = findViewById(R.id.changePasswordButton)

        backButton.setOnClickListener{
            finish()
        }

        changePasswordButton.setOnClickListener {
            lifecycleScope.launch{
                handleChangePassword()
            }
        }
    }


    private suspend fun handleChangePassword() {
        val oldPassword : TextView = findViewById(R.id.oldPasswordFieldChangePassword)
        val newPassword : TextView = findViewById(R.id.newPasswordFieldChangePassword)
        val confirmNewPassword :TextView = findViewById(R.id.confirmPasswordFieldChangePassword)

        withContext(Dispatchers.IO) {
            if (changePasswordViewModel.changePassword(oldPassword.text.toString(), newPassword.text.toString())) {
                runOnUiThread {
                    showSnackBar()
                }
                TimeUnit.SECONDS.sleep(1L);
                finish()
            }
        }
    }

    private fun showSnackBar() {
        val signupLayout = findViewById<ConstraintLayout>(R.id.rootLayout)
        Snackbar.make(signupLayout, "Password changed!", Snackbar.LENGTH_LONG).show();
    }
}


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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.databinding.ActivityLoginBinding
import com.example.workflow_manager_frontend.databinding.ActivitySignUpBinding
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

        val binding: ActivitySignUpBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.viewModel = signUpViewModel
        binding.lifecycleOwner = this

        signUpViewModel.success.observe(this) {
            if (!it) return@observe
            Log.d(tag, "sign up successfully");
            runOnUiThread {
                showSnackBar()
            }
            TimeUnit.SECONDS.sleep(1L);
            createLoginIntent()
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
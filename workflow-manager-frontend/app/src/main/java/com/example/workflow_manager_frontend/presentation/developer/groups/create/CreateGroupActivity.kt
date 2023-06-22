package com.example.workflow_manager_frontend.presentation.developer.groups.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.databinding.ActivityCreateGroupBinding
import com.example.workflow_manager_frontend.databinding.ActivitySignUpBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CreateGroupActivity : AppCompatActivity() {

    private val createGroupViewModel : CreateGroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityCreateGroupBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_create_group)
        binding.viewModel = createGroupViewModel
        binding.lifecycleOwner = this

        createGroupViewModel.success.observe(this){
            if (!it) return@observe
            Log.d("", "CreatedGroupSuccessfully");
            runOnUiThread {
                showSnackBar()
            }
            TimeUnit.SECONDS.sleep(1L);
            finish()
        }
    }

    private fun showSnackBar() {
        val signupLayout = findViewById<ConstraintLayout>(R.id.rootLayout)
        val snackbar =
            Snackbar.make(signupLayout, "Successfully created group!", Snackbar.LENGTH_LONG)
        snackbar.show()
    }
}
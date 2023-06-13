package com.example.workflow_manager_frontend.presentation.developer.groups.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.example.workflow_manager_frontend.R
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
        setContentView(R.layout.activity_create_group)

        val createGroupButton = findViewById<Button>(R.id.createGroupButton)
        val groupNameText = findViewById<EditText>(R.id.groupNameEditText)
        val groupDescriptionText = findViewById<EditText>(R.id.groupDescriptionEditText)

        createGroupButton.setOnClickListener {

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    if(createGroupViewModel.createGroup(
                            groupNameText.text.toString(), groupDescriptionText.text.toString())) {
                            Log.d("", "CreatedGroupSuccessfully");
                            runOnUiThread {
                                showSnackBar()
                            }
                            TimeUnit.SECONDS.sleep(1L);
                            finish()
                    }
                }
            }
        }
    }

    private fun showSnackBar() {
        val signupLayout = findViewById<ConstraintLayout>(R.id.rootLayout)
        val snackbar =
            Snackbar.make(signupLayout, "Successfully created group!", Snackbar.LENGTH_LONG)
        snackbar.show()
    }
}
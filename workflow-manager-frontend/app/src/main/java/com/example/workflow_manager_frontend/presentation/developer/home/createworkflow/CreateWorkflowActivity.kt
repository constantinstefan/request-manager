package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.steps.StepsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CreateWorkflowActivity : AppCompatActivity() {

    private val stepsViewModel: StepsViewModel by viewModels()
    private val sharingViewModel: SharingViewModel by viewModels()
    private val createWorkflowViewModel: CreateWorkflowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createWorkflowViewModel.setStepsViewModel(stepsViewModel)
        createWorkflowViewModel.setSharingViewModel(sharingViewModel)

        setContentView(R.layout.activity_create_workflow)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager.adapter = CreateWorkflowPagerAdapter(this, stepsViewModel, sharingViewModel)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Steps"
                1 -> tab.text = "Sharing"
            }
        }.attach()

        val saveIcon = findViewById<ImageView>(R.id.saveIcon)
        saveIcon.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    if (createWorkflowViewModel.createWorkflow()) {
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
        val rootLayout = findViewById<ConstraintLayout>(R.id.createActicityRoot)
        val snackbar = Snackbar.make(rootLayout, "Created workflow successfully!", Snackbar.LENGTH_SHORT)
        snackbar.show();
    }
}
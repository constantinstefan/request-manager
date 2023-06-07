package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.workflow_manager_frontend.domain.Workflow
import com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.steps.StepsFragment
import com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.steps.StepsViewModel

class CreateWorkflowPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val stepsViewModel: StepsViewModel,
    private val sharingViewModel: SharingViewModel,
    private val workflow: Workflow?
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StepsFragment(stepsViewModel, workflow)
            1 -> SharingFragment(sharingViewModel, workflow)
            else -> throw IllegalArgumentException("tab position not found")
        }
    }
}
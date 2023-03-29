package com.example.workflow_manager_frontend.presentation.workflow

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.workflow_manager_frontend.R
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkflowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workflow)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            0
        )

        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val dotsIndicator = findViewById<SpringDotsIndicator>(R.id.dotsIndicator)
        val workflowStateAdapter = WorkflowStateAdapter(this as FragmentActivity)
        workflowStateAdapter.addFragment(CameraFirstFragment())
        workflowStateAdapter.addFragment(EditableHtmlFragment())
        viewPager.adapter = workflowStateAdapter
        dotsIndicator.setViewPager2(viewPager)
    }
}
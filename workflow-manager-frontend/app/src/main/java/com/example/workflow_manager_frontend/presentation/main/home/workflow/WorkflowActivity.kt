package com.example.workflow_manager_frontend.presentation.main.home.workflow

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.WorkflowExecutionContext
import com.example.workflow_manager_frontend.presentation.main.home.workflow.document.CameraFirstFragment
import com.example.workflow_manager_frontend.presentation.main.home.workflow.editablehtml.EditableHtmlFragment
import com.example.workflow_manager_frontend.presentation.main.home.workflow.formfields.FormFieldsFragment
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
@Parcelize
class WorkflowActivity : AppCompatActivity(), NextFragmentListener, Parcelable{

    private val workflowViewModel : WorkflowViewModel by viewModels()

    private lateinit var viewPager: ViewPager2
    private lateinit var workflowExecutionContext: WorkflowExecutionContext
    private var workflowId = -1

    private val tag = "WorkflowActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workflow)

        ActivityCompat.requestPermissions(this,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 0)

        viewPager = findViewById(R.id.view_pager)
        val dotsIndicator = findViewById<SpringDotsIndicator>(R.id.dotsIndicator)

        workflowId = intent.getIntExtra("workflowId", -1)
        if(workflowId == -1) {
            return
        }

        workflowExecutionContext = WorkflowExecutionContext(mutableMapOf(), mutableMapOf())

        lifecycleScope.launch {
            val fragmentList = fetchFragments(workflowId, workflowExecutionContext)
            val workflowStateAdapter = createWorkflowStateAdapter(fragmentList)

            runOnUiThread {
                setupViewPagerAndDotsIndicator(viewPager, dotsIndicator, workflowStateAdapter)
            }
        }
    }

    private suspend fun fetchFragments(workflowId: Int, workflowExecutionContext: WorkflowExecutionContext): List<Fragment> = withContext(Dispatchers.IO) {
        workflowViewModel.getSteps(workflowId)?.mapNotNull { step ->
            when (step.stepType) {
                "DOCUMENT" -> CameraFirstFragment.newInstance(step.document, workflowExecutionContext, this@WorkflowActivity)
                "FORM_FIELDS" -> step.formFields?.let { formFields ->
                    FormFieldsFragment.newInstance(formFields, workflowExecutionContext, this@WorkflowActivity) }
                "EDITABLE_HTML" -> EditableHtmlFragment.newInstance(step.editableHtml, workflowExecutionContext, this@WorkflowActivity)
                else -> null
            }
        } ?: emptyList()
    }
    private fun createWorkflowStateAdapter(fragmentList: List<Fragment>): WorkflowStateAdapter {
        val workflowStateAdapter = WorkflowStateAdapter(this@WorkflowActivity as FragmentActivity)
        fragmentList.forEach{fragment ->
            workflowStateAdapter.addFragment(fragment)
        }
        return workflowStateAdapter
    }

    private fun setupViewPagerAndDotsIndicator(viewPager: ViewPager2, dotsIndicator: SpringDotsIndicator, workflowStateAdapter: WorkflowStateAdapter) {
        viewPager.isUserInputEnabled = false
        viewPager.adapter = workflowStateAdapter
        dotsIndicator.setViewPager2(viewPager)
    }

    override fun onNextFragment() {
        val currentPosition = viewPager.currentItem
        val itemCount = viewPager.adapter?.itemCount ?: return

        if (currentPosition < itemCount -1) {
            viewPager.setCurrentItem(currentPosition + 1, true)
            return
        }

        onNoNextFragment()
    }

    override fun onNoNextFragment() {
        Log.d(tag, "no next fragment")
        Log.d(tag, workflowExecutionContext.toString())
        lifecycleScope.launch{
            withContext(Dispatchers.IO) {
                workflowViewModel.executeWorkflow(workflowId.toLong(), workflowExecutionContext)
                finish()
            }
        }
    }

}
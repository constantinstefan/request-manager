package com.example.workflow_manager_frontend.presentation.developer.home.execution

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.workflow_manager_frontend.databinding.ActivityExecutionBinding
import com.example.workflow_manager_frontend.databinding.ActivityExecutionDetailsBinding
import com.example.workflow_manager_frontend.domain.Execution
import com.example.workflow_manager_frontend.domain.StepExecution
import com.example.workflow_manager_frontend.domain.Workflow
import dagger.hilt.android.AndroidEntryPoint
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class ExecutionDetailsActivity: AppCompatActivity() {

    private lateinit var binding: ActivityExecutionDetailsBinding

    private val executionDetailsViewModel : ExecutionDetailsViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExecutionDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val execution: Execution = intent.getParcelableExtra("execution")
            ?: return
        val workflow: Workflow = intent.getParcelableExtra("workflow")
            ?: return

        binding.executionNameTextView.text = "Execution #${execution.id}"

        binding.recycleViewExecutionDetails.adapter = ExecutionDetailsAdapter(this, ExecutionDetailsDiffCallback())
        binding.recycleViewExecutionDetails.layoutManager = LinearLayoutManager(this)
        executionDetailsViewModel.subscribeForExecutions(workflow.id)

        executionDetailsViewModel.getStepExecutions(execution).observe(this){
            val listAdapter = binding.recycleViewExecutionDetails.adapter
                as ListAdapter<StepExecution, ExecutionDetailsAdapter.ExecutionDetailsViewHolder>
            listAdapter.submitList(it)
            updateEndTime(it)
            ViewUtility.updateIcon(binding.icon, this, it[0].execution)
        }

        executionDetailsViewModel.getStaticExecution().observe(this) {
            binding.triggeredByTextView.text = it.customer?.email
            binding.startTimeTextView.text = helperConvertTimestampToDateTimeString(it.startTime)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateEndTime(stepExecution: MutableList<StepExecution>) {
        if(stepExecution[0].execution.status == "IN_PROGRESS") return
        val endTime = stepExecution[0].execution.endTime
        binding.endTimeTextView.text = helperConvertTimestampToDateTimeString(endTime)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun helperConvertTimestampToDateTimeString(timestamp: Long?) : String{
        if(timestamp == null) return ""
        val instant: Instant = Instant.ofEpochSecond(timestamp)
        val localDateTime: LocalDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return formatter.format(localDateTime)
    }
}
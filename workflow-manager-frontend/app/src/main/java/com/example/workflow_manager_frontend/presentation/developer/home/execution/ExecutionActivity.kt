package com.example.workflow_manager_frontend.presentation.developer.home.execution

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.workflow_manager_frontend.databinding.ActivityExecutionBinding
import com.example.workflow_manager_frontend.domain.Execution
import com.example.workflow_manager_frontend.domain.Workflow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExecutionActivity : AppCompatActivity(){

    private lateinit var binding: ActivityExecutionBinding

    private val executionViewModel : ExecutionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExecutionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workflow: Workflow = intent.getParcelableExtra("workflow")
            ?: return

        binding.workflowNameTextView.text = workflow.name

        binding.recycleViewExecution.adapter = ExecutionAdapter(this, ExecutionDiffCallback()){
            startExecutionDetailsActivityIntent(it, workflow)
        }
        binding.recycleViewExecution.layoutManager = LinearLayoutManager(this)

        executionViewModel.subscribeForExecutions(workflow.id)
        executionViewModel.getExecutions(workflow.id).observe(this){
            val listAdapter = binding.recycleViewExecution.adapter
                    as ListAdapter<Execution, ExecutionAdapter.ExecutionViewHolder>
            listAdapter.submitList(it)
        }
    }

    private fun startExecutionDetailsActivityIntent(execution: Execution?, workflow: Workflow?) {
        val intent = Intent(this, ExecutionDetailsActivity::class.java)
        intent.putExtra("workflow", workflow)
        intent.putExtra("execution", execution)
        startActivity(intent)
    }
}
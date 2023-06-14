package com.example.workflow_manager_frontend.presentation.developer.home.execution

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workflow_manager_frontend.databinding.ActivityExecutionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExecutionActivity : AppCompatActivity(){

    private lateinit var binding: ActivityExecutionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExecutionBinding.inflate(layoutInflater)

        binding.recycleViewExecution.adapter = ExecutionAdapter(ExecutionDiffCallback()){

        }
        binding.recycleViewExecution.layoutManager = LinearLayoutManager(this)
        super.onCreate(savedInstanceState)
    }
}
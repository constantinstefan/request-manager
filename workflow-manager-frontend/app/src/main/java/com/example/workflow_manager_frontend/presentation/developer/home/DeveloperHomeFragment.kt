package com.example.workflow_manager_frontend.presentation.developer.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.workflow_manager_frontend.databinding.FragmentDeveloperHomeBinding
import com.example.workflow_manager_frontend.domain.Workflow
import com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.CreateWorkflowActivity
import com.example.workflow_manager_frontend.presentation.main.home.HomeViewModel
import com.example.workflow_manager_frontend.presentation.main.home.WorkflowAdapter
import com.example.workflow_manager_frontend.presentation.main.home.WorkflowDiffCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeveloperHomeFragment(): Fragment() {
    private var _binding: FragmentDeveloperHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        _binding = FragmentDeveloperHomeBinding.inflate(inflater, container, false)
        binding.addFab.setOnClickListener {
            startCreateWorkflowActivityIntent(null)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    homeViewModel.filter(newText)
                }
                return true
            }
        })

        setUpRecycleView()
        setUpViewModel()

        return binding.root
    }

    private fun setUpRecycleView() {
        binding.recycleViewWorkflow.adapter = WorkflowAdapter(
            WorkflowDiffCallback()
        ) { workflow -> startCreateWorkflowActivityIntent(workflow) }
        binding.recycleViewWorkflow.layoutManager = LinearLayoutManager(context)
    }

    private fun setUpViewModel() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.state.observe(viewLifecycleOwner) { state ->
                run {
                    Log.d("workflow", state.toString())
                    val listAdapter = binding.recycleViewWorkflow.adapter as ListAdapter<Workflow, WorkflowAdapter.WorkflowViewHolder>
                    listAdapter.submitList(state.toMutableList())
                }
            }
        }
    }

    private fun startCreateWorkflowActivityIntent(workflow: Workflow?) {
        val intent = Intent(context, CreateWorkflowActivity::class.java)
        intent.putExtra("workflow", workflow)
        startActivity(intent)
    }

}
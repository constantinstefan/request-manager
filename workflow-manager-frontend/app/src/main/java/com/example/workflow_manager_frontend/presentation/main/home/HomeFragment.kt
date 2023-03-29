package com.example.workflow_manager_frontend.presentation.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.workflow_manager_frontend.databinding.FragmentHomeBinding
import com.example.workflow_manager_frontend.domain.model.Workflow
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class HomeFragment(
) : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    var workflowList: List<Workflow> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false);

        setUpRecycleView()
        setUpViewModel()
        return binding.root
    }

    private fun setUpRecycleView() {
        binding.recycleViewWorkflow.adapter = WorkflowAdapter(WorkflowDiffCallback())
        binding.recycleViewWorkflow.layoutManager = LinearLayoutManager(context)
    }

    private fun setUpViewModel() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.state.collect { state ->
                run {
                    Log.d("workflow", state.toString())
                    workflowList = state.toMutableList()
                    val listAdapter = binding.recycleViewWorkflow.adapter as ListAdapter<Workflow,WorkflowAdapter.WorkflowViewHolder>
                    listAdapter.submitList(state.toMutableList())
                }
            }
        }
    }

}
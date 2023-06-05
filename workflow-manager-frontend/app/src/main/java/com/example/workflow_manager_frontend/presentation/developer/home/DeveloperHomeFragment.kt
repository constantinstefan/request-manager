package com.example.workflow_manager_frontend.presentation.developer.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.workflow_manager_frontend.databinding.FragmentCameraFirstBinding
import com.example.workflow_manager_frontend.databinding.FragmentDeveloperHomeBinding
import com.example.workflow_manager_frontend.databinding.FragmentSharingBinding
import com.example.workflow_manager_frontend.presentation.developer.DeveloperMainActivity
import com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.CreateWorkflowActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeveloperHomeFragment(): Fragment() {
    private var _binding: FragmentDeveloperHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        _binding = FragmentDeveloperHomeBinding.inflate(inflater, container, false)
        binding.addFab.setOnClickListener {
            startCreateWorkflowActivityIntent()
        }

        return binding.root
    }

    private fun startCreateWorkflowActivityIntent() {
        val intent = Intent(context, CreateWorkflowActivity::class.java)
        startActivity(intent)
    }

}
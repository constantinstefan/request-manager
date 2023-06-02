package com.example.workflow_manager_frontend.presentation.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.databinding.FragmentHomeBinding
import com.example.workflow_manager_frontend.databinding.FragmentProfileBinding
import com.example.workflow_manager_frontend.presentation.auth.LoginViewModel
import com.example.workflow_manager_frontend.presentation.auth.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)

        binding.changePasswordCard.setOnClickListener {
            createChangePasswordActivity()
        }

        return binding.root
    }

    private fun createChangePasswordActivity() {
        val intent = Intent(requireContext(), ChangePasswordActivity::class.java)
        startActivity(intent)
    }

}
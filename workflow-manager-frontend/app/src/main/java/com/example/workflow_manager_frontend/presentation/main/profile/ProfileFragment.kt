package com.example.workflow_manager_frontend.presentation.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.databinding.FragmentHomeBinding
import com.example.workflow_manager_frontend.databinding.FragmentProfileBinding
import com.example.workflow_manager_frontend.presentation.auth.LoginActivity
import com.example.workflow_manager_frontend.presentation.auth.LoginViewModel
import com.example.workflow_manager_frontend.presentation.auth.SignUpActivity
import com.example.workflow_manager_frontend.presentation.auth.SignUpActivity_GeneratedInjector
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)

        profileViewModel.getState().observe(viewLifecycleOwner){
            binding.usernameTextView.text = it?.email
            binding.roleTextView.text = if(it?.role == "ROLE_CUSTOMER") "CUSTOMER"
                else "Developer"
        }

        binding.logout.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    profileViewModel.logout()
                }
            }
            createLoginActivity()
        }

        binding.changePasswordCard.setOnClickListener {
            createChangePasswordActivity()
        }

        return binding.root
    }

    private fun createChangePasswordActivity() {
        val intent = Intent(requireContext(), ChangePasswordActivity::class.java)
        startActivity(intent)
    }

    private fun createLoginActivity() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}
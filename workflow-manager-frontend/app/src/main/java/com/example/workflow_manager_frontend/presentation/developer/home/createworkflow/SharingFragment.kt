package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.databinding.FragmentSharingBinding
import com.example.workflow_manager_frontend.databinding.FragmentStepsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SharingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SharingFragment(
    private val sharingViewModel: SharingViewModel
) : Fragment() {
    private var _binding: FragmentSharingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        _binding = FragmentSharingBinding.inflate(inflater, container, false)

        sharingViewModel.setType(if(binding.visibilitySwitch.isChecked)
            "GROUP" else
                "PUBLIC")
        binding.visibilitySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharingViewModel.setType("GROUP")
                binding.groupAutoCompleteTextView.visibility = View.VISIBLE
            } else {
                sharingViewModel.setType("PUBLIC")
                binding.groupAutoCompleteTextView.visibility = View.GONE
            }
        }

        return binding.root
    }
}
package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workflow_manager_frontend.databinding.FragmentSharingBinding
import com.example.workflow_manager_frontend.domain.Group
import com.example.workflow_manager_frontend.domain.Workflow
import java.util.stream.Collectors

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
    private val sharingViewModel: SharingViewModel,
    private val workflow: Workflow?
) : Fragment() {
    private var _binding: FragmentSharingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        _binding = FragmentSharingBinding.inflate(inflater, container, false)

        val sharingType = workflow?.sharing?.sharingType ?: "PUBLIC"
        binding.visibilitySwitch.isChecked = (sharingType == "GROUP")
        sharingViewModel.setType(sharingType)
        if(sharingType == "GROUP") {
            binding.groupAutoCompleteTextView.visibility = View.VISIBLE
        }

        binding.visibilitySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharingViewModel.setType("GROUP")
                binding.groupAutoCompleteTextView.visibility = View.VISIBLE
            } else {
                sharingViewModel.setType("PUBLIC")
                binding.groupAutoCompleteTextView.visibility = View.GONE
            }
        }

        sharingViewModel.getGroups().observe(viewLifecycleOwner) { groups ->
            val adapter = GroupAutocompleteAdapter(requireContext(), groups)
            binding.groupAutoCompleteTextView.setAdapter(adapter)
        }

        binding.groupAutoCompleteTextView.setOnItemClickListener { adapterView, _, position, _ ->
            val selectedItem = adapterView.getItemAtPosition(position) as Group
            sharingViewModel.selectedGroup = selectedItem
        }

        return binding.root
    }
}
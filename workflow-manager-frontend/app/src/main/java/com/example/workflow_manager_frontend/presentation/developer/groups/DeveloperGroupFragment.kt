package com.example.workflow_manager_frontend.presentation.developer.groups

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.workflow_manager_frontend.databinding.FragmentDeveloperGroupBinding
import com.example.workflow_manager_frontend.domain.Group
import com.example.workflow_manager_frontend.presentation.developer.groups.create.CreateGroupActivity
import com.example.workflow_manager_frontend.presentation.developer.groups.details.GroupDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale.filter


@AndroidEntryPoint
class DeveloperGroupFragment : Fragment() {
    private var _binding: FragmentDeveloperGroupBinding? = null
    private val binding get() = _binding!!

    private val groupViewModel : GroupViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        _binding = FragmentDeveloperGroupBinding.inflate(inflater, container, false)
        setUpRecycleView()

        binding.addFab.setOnClickListener {
            startCreateGroupActivityIntent()
        }

        groupViewModel.getGroups().observe(viewLifecycleOwner){groups ->
            val listAdapter = binding.recycleViewGroup.adapter as ListAdapter<Group, GroupAdapter.GroupViewHolder>
            listAdapter.submitList(groups.toMutableList())
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    groupViewModel.filter(newText)
                }
                return true
            }
        })
        return binding.root
    }

    private fun setUpRecycleView() {
        binding.recycleViewGroup.adapter = GroupAdapter(
            GroupDiffCallback()
        ) { group -> startGroupDetailsActivityIntent(group) }
        binding.recycleViewGroup.layoutManager = LinearLayoutManager(context)
    }

    private fun startCreateGroupActivityIntent() {
        val intent = Intent(context, CreateGroupActivity::class.java)
        startActivity(intent)
    }

    private fun startGroupDetailsActivityIntent(group: Group) {
        val intent = Intent(context, GroupDetailsActivity::class.java)
        intent.putExtra("group", group)
        startActivity(intent)
    }

}
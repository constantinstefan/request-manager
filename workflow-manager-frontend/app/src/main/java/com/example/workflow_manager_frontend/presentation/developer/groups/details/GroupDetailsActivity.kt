package com.example.workflow_manager_frontend.presentation.developer.groups.details

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.data.repository.GroupRepository
import com.example.workflow_manager_frontend.domain.Customer
import com.example.workflow_manager_frontend.domain.Group
import com.example.workflow_manager_frontend.presentation.developer.groups.GroupAdapter
import com.example.workflow_manager_frontend.presentation.developer.groups.GroupDiffCallback
import com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.GroupAutocompleteAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class GroupDetailsActivity() : AppCompatActivity() {

    private val groupDetailsViewModel: GroupDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_details)

        val group = intent.getParcelableExtra<Group>("group")
        if(group?.id == null) {
            return
        }

        val groupNameTextView = findViewById<TextView>(R.id.groupNameTextView)
        val groupDetailsTextView = findViewById<TextView>(R.id.groupDescriptionTextView)
        val recyclerView = findViewById<RecyclerView>(R.id.recycle_view_group)
        val memberAutoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.memberAutoCompleteTextView)
        val addFieldIcon = findViewById<ImageView>(R.id.addFieldIcon)

        recyclerView.adapter = GroupMemberAdapter(GroupMemberDiffCallback(), this@GroupDetailsActivity) { memberToRemove ->
            Log.d("", memberToRemove.toString())
            lifecycleScope.launch{
                groupDetailsViewModel.removeMember(group.id, memberToRemove)
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        groupNameTextView.text = group?.name
        groupDetailsTextView.text = group?.description

        groupDetailsViewModel.getMembers(group.id).observe(this@GroupDetailsActivity){
            val listAdapter = recyclerView.adapter as ListAdapter<Customer, GroupMemberAdapter.GroupMemberViewHolder>
            listAdapter.submitList(it)
        }

        lifecycleScope.launch {
            groupDetailsViewModel.setGroup(group)
        }


        groupDetailsViewModel.getUsers().observe(this){users ->
            val autocompleteAdapter = MemberAutocompleteAdapter(this, users)
            memberAutoCompleteTextView.setAdapter(autocompleteAdapter)
        }

        memberAutoCompleteTextView.setOnItemClickListener { adapterView, _, position, _ ->
            val selectedItem = adapterView.getItemAtPosition(position) as Customer
            groupDetailsViewModel.selectedMember.value = selectedItem
        }

        addFieldIcon.setOnClickListener {
            lifecycleScope.launch {
                groupDetailsViewModel.addMember(group.id, groupDetailsViewModel.selectedMember.value)
                groupDetailsViewModel.selectedMember.value = null
                memberAutoCompleteTextView.setText("")
            }
        }
    }
}
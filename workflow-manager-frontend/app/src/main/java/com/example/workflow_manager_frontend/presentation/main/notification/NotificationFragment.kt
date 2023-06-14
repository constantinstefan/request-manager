package com.example.workflow_manager_frontend.presentation.main.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.workflow_manager_frontend.databinding.FragmentNotificationBinding
import com.example.workflow_manager_frontend.domain.Notification
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment: Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val notificationViewModel : NotificationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = FragmentNotificationBinding.inflate(inflater,container,false)

        binding.recycleViewNotification.adapter = NotificationAdapter(NotificationDiffCallback())
        binding.recycleViewNotification.layoutManager = LinearLayoutManager(context)

        notificationViewModel.getAppNotifications().observe(viewLifecycleOwner){
            val listAdapter = binding.recycleViewNotification.adapter as ListAdapter<Notification, NotificationAdapter.NotificationViewHolder>
            listAdapter.submitList(it)
        }

        notificationViewModel.readNotifications()

        return binding.root
    }
}
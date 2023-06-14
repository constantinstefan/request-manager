package com.example.workflow_manager_frontend.presentation.main.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.Notification

class NotificationAdapter(
    notificationDiffCallback: NotificationDiffCallback
)  : ListAdapter<Notification, NotificationAdapter.NotificationViewHolder>(
    notificationDiffCallback) {

    inner class NotificationViewHolder(
        itemView : View,
    ) : RecyclerView.ViewHolder(itemView) {
        var title: TextView

        init {
            title = itemView.findViewById(R.id.notificationTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification,parent,false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = getItem(position)
        holder.title.text = notification.message
    }
}
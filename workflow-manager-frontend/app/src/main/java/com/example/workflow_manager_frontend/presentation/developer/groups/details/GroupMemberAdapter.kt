package com.example.workflow_manager_frontend.presentation.developer.groups.details

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.Customer
import com.google.android.material.card.MaterialCardView

class GroupMemberAdapter(
    groupMemberDiffCallback: GroupMemberDiffCallback,
    private val context: Context,
    private val onMemberRemoved: (Customer) -> Unit
    ) : ListAdapter<Customer, GroupMemberAdapter.GroupMemberViewHolder>(
    groupMemberDiffCallback) {

        @RequiresApi(Build.VERSION_CODES.Q)
        inner class GroupMemberViewHolder(
            itemView : View,
            private val onMemberRemoved : (Customer)-> Unit
        ) : RecyclerView.ViewHolder(itemView) {
            val title: TextView
            val more: ImageView

            init {
                title = itemView.findViewById(R.id.item_group_member_title)
                more = itemView.findViewById(R.id.item_group_member_more)

                more.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val customer = getItem(position)
                        val popup = PopupMenu(context, more)
                        popup.menuInflater.inflate(R.menu.group_member_option_menu, popup.menu)
                        popup.setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.menu_item_remove_member -> {
                                    onMemberRemoved(customer)
                                    true
                                }
                                else -> false
                            }
                        }
                        popup.setForceShowIcon(true)
                        popup.show()
                    }
                }
            }
        }


        @RequiresApi(Build.VERSION_CODES.Q)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMemberViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group_member,parent,false)
            return GroupMemberViewHolder(view, onMemberRemoved)
        }


        @RequiresApi(Build.VERSION_CODES.Q)
        override fun onBindViewHolder(holder: GroupMemberViewHolder, position: Int) {
            val customer = getItem(position)
            holder.title.text = customer.email
        }
}
package com.example.workflow_manager_frontend.presentation.developer.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.Group
import com.google.android.material.card.MaterialCardView

class GroupAdapter (
        groupDiffCallback: GroupDiffCallback,
        private val onItemViewClicked: (Group) -> Unit
    ) : ListAdapter<Group, GroupAdapter.GroupViewHolder>(
        groupDiffCallback) {

        inner class GroupViewHolder(
            itemView : View,
            private val onItemViewClicked : (Group)-> Unit
        ) : RecyclerView.ViewHolder(itemView) {
            val title: TextView
            val card: MaterialCardView

            init {
                title = itemView.findViewById(R.id.item_group_title)
                card = itemView.findViewById(R.id.item_group_card)

                card.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val group = getItem(position)
                        onItemViewClicked(group)
                    }
                }
            }
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group,parent,false)
            return GroupViewHolder(view, onItemViewClicked)
        }


        override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
            val group = getItem(position)
            holder.title.text = group.name
        }
    }
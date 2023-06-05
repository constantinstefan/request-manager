package com.example.workflow_manager_frontend.presentation.developer.home.createworkflow.steps

import android.content.Context
import android.os.Build
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import com.example.workflow_manager_frontend.R

class OptionsMenuHelper {
    companion object {
        @RequiresApi(Build.VERSION_CODES.Q)
        fun setupOptionsMenu(context: Context, options: ImageView, onDeleteAction: () -> Unit) {
            options.setOnClickListener { view ->
                val popup = PopupMenu(context, options)
                popup.menuInflater.inflate(R.menu.options1_menu, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_item_delete -> {
                            onDeleteAction.invoke()
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
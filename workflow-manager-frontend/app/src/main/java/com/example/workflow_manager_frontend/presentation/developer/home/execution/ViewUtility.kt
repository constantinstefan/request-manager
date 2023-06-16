package com.example.workflow_manager_frontend.presentation.developer.home.execution

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.workflow_manager_frontend.R
import com.example.workflow_manager_frontend.domain.Execution

class ViewUtility {
    companion object{
        fun updateIcon(icon: ImageView, context: Context, execution: Execution) {
            when(execution.status) {
                "IN_PROGRESS" -> {
                    icon.setColorFilter(ContextCompat.getColor(context, R.color.purple_primary))
                    icon.setImageResource(R.drawable.baseline_hourglass_top_24)
                }
                "SUCCESS" -> {
                    icon.setColorFilter(ContextCompat.getColor(context, R.color.green_secondary))
                    icon.setImageResource(R.drawable.baseline_check_24)
                }
                "FAILURE" -> {
                    icon.setColorFilter(ContextCompat.getColor(context, R.color.red_error))
                    icon.setImageResource(R.drawable.baseline_warning_24)
                }
                else -> Log.e("icon", "unexpected status ${execution.status}")
            }
        }
    }
}
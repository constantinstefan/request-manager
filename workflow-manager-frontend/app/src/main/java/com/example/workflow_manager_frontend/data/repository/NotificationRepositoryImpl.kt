package com.example.workflow_manager_frontend.data.repository

import android.util.Log
import com.example.workflow_manager_frontend.data.source.network.RetrofitInstance
import com.example.workflow_manager_frontend.domain.Notification
import java.io.IOException

class NotificationRepositoryImpl : NotificationRepository {
    private val tag = "NotificationRepository"

    override suspend fun getNotifications(): List<Notification>? {

        try{
            val response = RetrofitInstance.api.getNotifications()
            if(! response.isSuccessful) {
                Log.e(tag, response.toString())
                return emptyList()
            }
            return response.body()
        }
        catch (e: IOException) {
            Log.e(tag, e.toString())
        }
        return emptyList()
    }
}
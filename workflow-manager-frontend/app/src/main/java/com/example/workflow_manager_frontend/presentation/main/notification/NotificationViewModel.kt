package com.example.workflow_manager_frontend.presentation.main.notification

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workflow_manager_frontend.data.repository.NotificationRepository
import com.example.workflow_manager_frontend.data.source.network.NotificationClient
import com.example.workflow_manager_frontend.domain.Notification
import com.example.workflow_manager_frontend.domain.converter.NotificationConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel(){

    private var notificationClient: NotificationClient = NotificationClient()

    val notifications: MutableLiveData<MutableList<Notification>> = MutableLiveData()

    val notificationCount : MutableLiveData<Int> = MutableLiveData()

    init{
        viewModelScope.launch {
            notifications.value = notificationRepository.getNotifications() as MutableList<Notification>?
        }

        notificationCount.value = 0

        notificationClient.subscribe("/notifications") {
            addNotification(it.payload)
        }
    }

    fun addNotification(notification: String) {
        val currentNotifications = notifications.value.orEmpty().toMutableList()
        NotificationConverter.fromString(notification)?.let { currentNotifications.add(0,it) }
        notifications.postValue(currentNotifications)
        notificationCount.postValue(notificationCount.value?.plus(1))
    }

    fun readNotifications() {
        notificationCount.value = 0;
    }

    fun getAppNotificationCount(): MutableLiveData<Int> {
        return notificationCount
    }

    fun getAppNotifications() : MutableLiveData<MutableList<Notification>> {
        return notifications
    }
}
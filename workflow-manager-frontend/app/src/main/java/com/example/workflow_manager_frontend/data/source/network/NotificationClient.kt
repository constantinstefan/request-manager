package com.example.workflow_manager_frontend.data.source.network

import android.util.Log
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient

class NotificationClient {

    private lateinit var stompClient: StompClient

    private val tag = "NotificationClient"

    fun subscribe() {
        stompClient =
            Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://10.0.2.2:8080/ws/websocket");
        stompClient.connect()


        stompClient.topic("/execution").subscribe { topicMessage ->
            Log.d(tag, topicMessage.getPayload())
        }
    }

        fun unsubscribe() {
            stompClient.disconnect()
        }
}
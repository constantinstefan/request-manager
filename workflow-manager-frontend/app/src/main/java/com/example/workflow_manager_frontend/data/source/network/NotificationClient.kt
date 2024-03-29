package com.example.workflow_manager_frontend.data.source.network

import android.util.Log
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompMessage
import java.util.function.Consumer

class NotificationClient {

    private lateinit var stompClient: StompClient

    private val tag = "NotificationClient"

    fun subscribe(topic: String, onMessage: (StompMessage) -> Unit) {
        stompClient =
            Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://10.0.2.2:8080/ws/websocket");
        stompClient.connect()


        stompClient.topic(topic).subscribe { topicMessage ->
            Log.d(tag, topicMessage.getPayload())
            onMessage(topicMessage)
        }

    }

        fun unsubscribe() {
            stompClient.disconnect()
        }
}
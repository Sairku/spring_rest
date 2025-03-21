package com.example.spring_rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendAccountUpdate(String accountNumber, String message) {
        messagingTemplate.convertAndSend("/topic/account/" + accountNumber, message);
    }
}
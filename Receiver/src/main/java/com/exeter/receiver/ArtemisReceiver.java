package com.exeter.receiver;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ArtemisReceiver {
	
	@JmsListener(destination = "${jms.queue.name}")
	public void receiveMessage(String message) {
		System.out.println("Message Received: " + message);
	}
}

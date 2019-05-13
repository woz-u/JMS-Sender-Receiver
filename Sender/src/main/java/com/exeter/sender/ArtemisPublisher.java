package com.exeter.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArtemisPublisher {

	@Autowired
	JmsTemplate template;
	
	@Value("${jms.queue.name}")
	String queueName;
	
	public void sendMessage(String message) {
		template.convertAndSend(queueName, message);
	}
}

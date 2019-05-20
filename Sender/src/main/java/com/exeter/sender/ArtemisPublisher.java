package com.exeter.sender;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.exeter.pojos.ProductOrder;

@Component
public class ArtemisPublisher {

	@Autowired
	@Qualifier("DefaultJmsTemplate")
	JmsTemplate template;
	
	@Autowired
	@Qualifier("jmsTopicTemplate")
	JmsTemplate topicTemplate;
	
	@Autowired
	@Qualifier("jmsJSONTopicTemplate")
	JmsTemplate JSONTopicTemplate;

	@Value("${jms.queue.name}")
	String queueName;

	@Value("${jms.replyqueue.name}")
	String replyQueueName;

	public void sendMessage(String message) {
		template.send(queueName, s -> {
			Queue replyQueue = s.createQueue(replyQueueName);
			TextMessage msg = s.createTextMessage(message);
			msg.setJMSType("text");
			msg.setJMSReplyTo(replyQueue);
			return msg;
		});
	}

	public void sendBytesMessage(String message) {
		template.send(queueName, s -> {
			BytesMessage msg = s.createBytesMessage();
			msg.setJMSType("bytes");
			msg.writeBytes(message.getBytes());
			return msg;
		});
	}

	@JmsListener(destination = "${jms.replyqueue.name}")
	public void onReplyQueueMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				System.out.println("Reply Received: " + textMessage.getText());
				template.setPubSubDomain(true);
				template.send("topicTextMessages", s -> s.createTextMessage("Publishing: " + textMessage.getText()));
				template.setPubSubDomain(false);
			}
		} catch (JMSException e) {
			System.out.println("Error receiving Reply: " + e.getMessage());
		}
	}
	
	public void broadcastProductOrder(ProductOrder order) {
	    JSONTopicTemplate.convertAndSend("ProductOrders", order);
	}
}

package com.exeter.receiver;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.exeter.pojos.ProductOrder;

@Component
public class ArtemisReceiver {

	@Autowired
	JmsTemplate template;

	@JmsListener(destination = "${jms.queue.name}", selector = "JMSType='bytes'")
	public void receiveBytesMessage(BytesMessage bytesMessage) {
		try {
			byte[] byteArr = new byte[(int) bytesMessage.getBodyLength()];
			bytesMessage.readBytes(byteArr);
			String stringMessage = new String(byteArr);
			System.out.println("Message Received: " + stringMessage);
		} catch (JMSException e) {
			System.out.println("Error receiving Message: " + e.getMessage());
		}
	}

	@JmsListener(destination = "${jms.queue.name}", selector = "JMSType='text'")
	public void receiveTextMessage(TextMessage message) {
		try {
			System.out.println("Message Received: " + message.getText());
			if (message.getJMSReplyTo() != null) {
				template.convertAndSend(message.getJMSReplyTo(), "Message Acknowledged");
			}

		} catch (JMSException e) {
			System.out.println("Error receiving Message: " + e.getMessage());
		}
	}
	
	@JmsListener(destination = "topicTextMessages", containerFactory = "myFactory")
	public void receiveTopicTextMessage(TextMessage message) {
		try {
			System.out.println("Topic Message Received: " + message.getText());
		} catch (JMSException e) {
			System.out.println("Error receiving Topic Message: " + e.getMessage());
		}
	}
	
	@JmsListener(destination = "ProductOrders", containerFactory = "ProductOrdersFactory")
	public void listenProductOrder(ProductOrder productOrder) {
		System.out.println("Name: " +  productOrder.getProductName() + " Quantity: " + productOrder.getQuantity() + " Price: " + productOrder.getPrice());
	}
}

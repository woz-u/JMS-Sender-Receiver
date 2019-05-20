package com.exeter.sender;

import javax.jms.ConnectionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@SpringBootApplication
public class SenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SenderApplication.class, args);
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
	
	@Bean
	public JmsTemplate jmsTopicTemplate(ConnectionFactory connectionFactory) {
		JmsTemplate topicTemplate = new JmsTemplate(connectionFactory);
		topicTemplate.setPubSubDomain(true);
		return topicTemplate;
	}
	
	@Bean
	public JmsTemplate jmsJSONTopicTemplate(ConnectionFactory connectionFactory) {
		JmsTemplate topicTemplate = new JmsTemplate(connectionFactory);
		topicTemplate.setPubSubDomain(true);
		topicTemplate.setMessageConverter(jacksonJmsMessageConverter());
		return topicTemplate;
	}
	
	@Bean
	public JmsTemplate DefaultJmsTemplate(ConnectionFactory connectionFactory) {
		JmsTemplate template = new JmsTemplate(connectionFactory);
		return template;
	}
}

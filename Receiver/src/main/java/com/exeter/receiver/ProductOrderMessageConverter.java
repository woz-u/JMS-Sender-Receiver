package com.exeter.receiver;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConversionException;

import com.exeter.pojos.ProductOrder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductOrderMessageConverter extends MappingJackson2MessageConverter {

	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		return converter.toMessage(object, session);
	}

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		ObjectMapper objMap = new ObjectMapper();
		try {
			objMap.readValue(message.toString(), ProductOrder.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return converter.fromMessage(message);
	}

}

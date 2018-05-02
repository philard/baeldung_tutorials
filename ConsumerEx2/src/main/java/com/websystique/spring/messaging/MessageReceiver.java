package com.websystique.spring.messaging;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.websystique.spring.model.Product;
import com.websystique.spring.service.OrderService;

@Component
public class MessageReceiver {

	static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);
	private static final String ORDER_QUEUE = "order-queue";
	
	@Autowired
	OrderService orderService;

	@JmsListener(destination = ORDER_QUEUE)
	public void receiveMessage(final Message<Product> message) throws JMSException {
		LOG.info("----------------------------------------------------");
		MessageHeaders headers =  message.getHeaders();
		LOG.info("Application : headers received : {}", headers);
		
		Product product = message.getPayload();
		LOG.info("Application : product : {}",product);	

		orderService.processOrder(product);
		LOG.info("----------------------------------------------------");

	}
	
}

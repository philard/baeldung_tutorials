package org.phil;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.LinkedList;

//import org.springframework.messaging.Message;

public class PhilsClient {

    private LinkedList<Message> consumedElements;

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                "org.phil");

        final Object myDestination = context.getBean("jmsListenerContainerFactory");
//        final Object myDestination = context.getBean("myDestination");
    }

    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "myDestination")
    public void sampleJmsListenerMethod(Message message) {
        consumedElements.add(message);
        if (message instanceof TextMessage) {
            try {
                String msg = ((TextMessage) message).getText();
                System.out.println("PhilsClient received message: " + msg);
            } catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    }

package com.baeldung.spring.jms;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.JMSException;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleListenerTest {

    private static SampleJmsMessageSender messageProducer;
    private static SampleListener listener;

    @SuppressWarnings("resource")
    @BeforeClass
    public static void setUp() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:EmbeddedActiveMQ.xml",
                "classpath:applicationContext.xml");
        messageProducer = (SampleJmsMessageSender) applicationContext.getBean("SampleJmsMessageSender");
        listener = (SampleListener) applicationContext.getBean("messageListener");
    }

    @Test
    public void receiveMessage() throws JMSException {

        Runnable delayed = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            messageProducer.sendMessage(new Employee("phil", 29));
        };

//        assert false; //TODO fix me
        final Employee employee = listener.receiveMessage();
        assertThat(employee).isNotNull();
    }
}
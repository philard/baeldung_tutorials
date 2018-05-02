package com.baeldung.spring.jms;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static com.jayway.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;

public class DefaultTextMessageSenderIntegrationTest {

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
    public void testSimpleSend() {
        final int before = listener.consumedElements.size();
        messageProducer.simpleSend();

        await().atMost(1000, MILLISECONDS).until(() ->
                assertThat(listener.consumedElements).hasSize(before + 1));
    }

    @Test
    public void testCompose() {
        final int before = listener.consumedElements.size();
        messageProducer.sendMessage(new Employee("phil", 29));

        await().atMost(1000, MILLISECONDS).until(() ->
                assertThat(listener.consumedElements).hasSize(before + 1));
    }

    @Test
    public void testSendMessageAsMap() {
        final int before = listener.consumedElements.size();
        messageProducer.sendMessageAsMap(new Employee("phil", 29));

        await().atMost(1000, MILLISECONDS).until(() ->
                assertThat(listener.consumedElements).hasSize(before + 1));
    }
}

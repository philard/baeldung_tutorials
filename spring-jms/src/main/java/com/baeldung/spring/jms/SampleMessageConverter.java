package com.baeldung.spring.jms;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Map;

public class SampleMessageConverter implements MessageConverter {

    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        MapMessage message = session.createMapMessage();
        if(object instanceof Employee) {
            Employee employee = (Employee) object;
            message.setString("name", employee.getName());
            message.setInt("age", employee.getAge());
        } else {
            Map employee = (Map) object;
            message.setString("name", (String) employee.get("name"));
            message.setInt("age", (Integer) employee.get("age"));
        }
        return message;
    }

    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        MapMessage mapMessage = (MapMessage) message;
        return new Employee(mapMessage.getString("name"), mapMessage.getInt("age"));
    }

}

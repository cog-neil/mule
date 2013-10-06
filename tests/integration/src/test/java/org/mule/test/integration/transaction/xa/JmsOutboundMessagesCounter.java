package org.mule.test.integration.transaction.xa;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.junit.Ignore;
import org.springframework.jms.support.JmsUtils;

@Ignore
public class JmsOutboundMessagesCounter implements TransactionScenarios.OutboundMessagesCounter
    {

        private MessageConsumer consumer;
        private boolean initialized;
        private int numberOfMessagesArrived;
        private String brokerUrl;
        private Connection connection;


    public static JmsOutboundMessagesCounter createVerifierForBroker(int port)
    {
        return new JmsOutboundMessagesCounter("tcp://localhost:" + port);
    }

    private JmsOutboundMessagesCounter(String brokerUrl)
    {
        this.brokerUrl = brokerUrl;
    }

    public void initialize()
    {
        if (!this.initialized)
        {
            ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
            cf.setBrokerURL(brokerUrl);
            try
            {
                connection = cf.createConnection();
                connection.start();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                consumer = session.createConsumer(session.createQueue("out"));
            }
            catch (JMSException e)
            {
                throw new RuntimeException(e);
            }
            this.initialized = true;
        }
    }

    @Override
    public int numberOfMessagesThatArrived() throws Exception
    {
        initialize();
        while (true)
        {
            System.out.println("#### Requester: Trying to receive message");
            Message message = consumer.receive(100);
            if (message != null)
            {
                numberOfMessagesArrived++;
                System.out.println("#### Requester: message received " + numberOfMessagesArrived);
            }
            else
            {
                break;
            }
        }
        System.out.println("##### message received " + numberOfMessagesArrived + " of " + NUMBER_OF_MESSAGES);
        return numberOfMessagesArrived;
    }

    @Override
    public void close()
    {
        JmsUtils.closeMessageConsumer(consumer);
        JmsUtils.closeConnection(connection);
        consumer = null;
        this.initialized = false;
        numberOfMessagesArrived = 0;
    }
}

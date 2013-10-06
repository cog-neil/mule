/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.jms.xa;

import org.mule.transaction.XaTransaction;

import java.lang.reflect.Proxy;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.XAConnection;
import javax.jms.XAConnectionFactory;
import javax.jms.XAQueueConnection;
import javax.jms.XAQueueConnectionFactory;
import javax.jms.XATopicConnection;
import javax.jms.XATopicConnectionFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BitronixConnectionFactoryWrapper
        implements ConnectionFactory
{
    /**
     * logger used by this class
     */
    protected static final transient Log logger = LogFactory.getLog(BitronixConnectionFactoryWrapper.class);

    protected final ConnectionFactory factory;

    public BitronixConnectionFactoryWrapper(ConnectionFactory factory)
    {
        this.factory = factory;
    }

    public Connection createConnection() throws JMSException
    {
        Connection xac = factory.createConnection();
        Connection proxy = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(),
                                                               new Class[]{Connection.class},
                                                               new BitronixConnectionInvocationHandler(xac));
        return proxy;
    }

    public Connection createConnection(String username, String password) throws JMSException
    {
        Connection xac = factory.createConnection(username, password);
        Connection proxy = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(),
                                                               new Class[]{Connection.class},
                                                               new BitronixConnectionInvocationHandler(xac));
        return proxy;
    }

}

package org.mule.transport.jms.xa;

import java.lang.reflect.Method;

import bitronix.tm.resource.jms.MessageConsumerWrapper;

/**
 *
 */
public class BitronixMessageConsumerInvocationHandler implements TargetInvocationHandler
{

    private final MessageConsumerWrapper messageConsumerWrapper;

    public BitronixMessageConsumerInvocationHandler(MessageConsumerWrapper messageConsumerWrapper)
    {
        this.messageConsumerWrapper = messageConsumerWrapper;
    }

    @Override
    public Object getTargetObject()
    {
        return messageConsumerWrapper;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        if (method.getName().equals("close"))
        {
            messageConsumerWrapper.getMessageConsumer().close();
            return null;
        }
        return method.invoke(messageConsumerWrapper,args);
    }
}

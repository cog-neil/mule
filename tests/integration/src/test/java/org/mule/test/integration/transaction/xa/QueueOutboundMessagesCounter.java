package org.mule.test.integration.transaction.xa;

import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.api.context.MuleContextAware;

import org.junit.Ignore;

@Ignore
public class QueueOutboundMessagesCounter implements TransactionScenarios.OutboundMessagesCounter, MuleContextAware
{

    private int numberOfMessagesArrived;
    private MuleClient muleClient;

    @Override
    public int numberOfMessagesThatArrived() throws Exception
    {
        MuleMessage muleMessage;
        while (true)
        {
            System.out.println("#### Requester: Trying to receive message");
            muleMessage = muleClient.request("outboundRequester", 100);
            if (muleMessage != null)
            {
                numberOfMessagesArrived++;
                System.out.println("#### Requester: message received " + numberOfMessagesArrived + " content: " + muleMessage.getPayloadAsString());
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
        numberOfMessagesArrived = 0;
    }

    @Override
    public void setMuleContext(MuleContext context)
    {
        this.muleClient = context.getClient();
    }
}

package org.mule.transport.vm;

import org.mule.util.xa.AbstractXAResourceManager;
import org.mule.util.xa.DefaultXASession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.transaction.xa.XAResource;

import bitronix.tm.internal.XAResourceHolderState;
import bitronix.tm.recovery.RecoveryException;
import bitronix.tm.resource.common.ResourceBean;
import bitronix.tm.resource.common.XAResourceHolder;
import bitronix.tm.resource.common.XAResourceProducer;
import bitronix.tm.resource.common.XAStatefulHolder;
import bitronix.tm.utils.Scheduler;

public class VmXaResourceProducer extends ResourceBean implements XAResourceProducer
{

    private final String uniqueName;
    private final AbstractXAResourceManager xaResourceManager;
    private List<XAResource> xaResources = new ArrayList<XAResource>();
    private ReadWriteLock xaResourcesLock = new ReentrantReadWriteLock();

    public VmXaResourceProducer(String uniqueName, AbstractXAResourceManager xaResourceManager)
    {
        this.uniqueName = uniqueName;
        this.xaResourceManager = xaResourceManager;
        setTwoPcOrderingPosition(Scheduler.ALWAYS_LAST_POSITION);
    }

    @Override
    public String getUniqueName()
    {
        return uniqueName;
    }

    @Override
    public XAResourceHolderState startRecovery() throws RecoveryException
    {
        return new XAResourceHolderState(new VmXaResourceHolder(new DefaultXASession(xaResourceManager),this),this);
    }

    @Override
    public void endRecovery() throws RecoveryException
    {
    }

    @Override
    public void setFailed(boolean failed)
    {
    }

    @Override
    public XAResourceHolder findXAResourceHolder(XAResource xaResource)
    {
        Lock lock = xaResourcesLock.readLock();
        lock.lock();
        try
        {
            for (XAResource resource : xaResources)
            {
                if (resource == xaResource)
                {
                    return new VmXaResourceHolder(resource,this);
                }
            }
            return null;
        }
        finally
        {
            lock.unlock();
        }
    }

    @Override
    public void init()
    {
    }

    @Override
    public void close()
    {
    }

    @Override
    public XAStatefulHolder createPooledConnection(Object xaFactory, ResourceBean bean) throws Exception
    {
        return null;
    }

    @Override
    public Reference getReference() throws NamingException
    {
        return null;
    }

    public void addXaResource(XAResource session)
    {
        Lock lock = xaResourcesLock.writeLock();
        lock.lock();
        try
        {
            this.xaResources.add(session);
        }
        finally
        {
            lock.unlock();
        }
    }
}

package org.mule.transport.vm;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.xa.XAResource;

import bitronix.tm.BitronixXid;
import bitronix.tm.internal.XAResourceHolderState;
import bitronix.tm.resource.common.ResourceBean;
import bitronix.tm.resource.common.StateChangeListener;
import bitronix.tm.resource.common.XAResourceHolder;
import bitronix.tm.utils.Uid;

/**
 *
 */
public class VmXaResourceHolder implements XAResourceHolder
{

    private final XAResource xaResource;
    private final ResourceBean resourceBean;

    public VmXaResourceHolder(XAResource xaResource, ResourceBean resourceBean)
    {
        this.xaResource = xaResource;
        this.resourceBean = resourceBean;
    }

    @Override
    public XAResource getXAResource()
    {
        return xaResource;
    }

    @Override
    public Map<Uid, XAResourceHolderState> getXAResourceHolderStatesForGtrid(Uid gtrid)
    {
        return null;
    }

    @Override
    public void putXAResourceHolderState(BitronixXid xid, XAResourceHolderState xaResourceHolderState)
    {
    }

    @Override
    public void removeXAResourceHolderState(BitronixXid xid)
    {
    }

    @Override
    public boolean hasStateForXAResource(XAResourceHolder xaResourceHolder)
    {
        return false;
    }

    @Override
    public ResourceBean getResourceBean()
    {
        return resourceBean;
    }

    @Override
    public int getState()
    {
        return 0;
    }

    @Override
    public void setState(int state)
    {
    }

    @Override
    public void addStateChangeEventListener(StateChangeListener listener)
    {
    }

    @Override
    public void removeStateChangeEventListener(StateChangeListener listener)
    {
    }

    @Override
    public List<XAResourceHolder> getXAResourceHolders()
    {
        return null;
    }

    @Override
    public Object getConnectionHandle() throws Exception
    {
        return null;
    }

    @Override
    public void close() throws Exception
    {
    }

    @Override
    public Date getLastReleaseDate()
    {
        return null;
    }
}

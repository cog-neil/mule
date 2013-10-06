package org.mule.module.btm.transaction;

import org.mule.util.xa.DefaultXASession;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

/**
 *
 */
public class TransactionWrapper implements Transaction
{

    private final Transaction delegate;
    private final TransactionManagerWrapper transactionalManagerWrapper;

    public TransactionWrapper(Transaction transaction, TransactionManagerWrapper transactionManagerWrapper)
    {
        this.delegate = transaction;
        this.transactionalManagerWrapper = transactionManagerWrapper;
    }

    @Override
    public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException
    {
        delegate.commit();
    }

    @Override
    public boolean delistResource(XAResource xaResource, int i) throws IllegalStateException, SystemException
    {
        return delegate.delistResource(xaResource, i);
    }

    @Override
    public boolean enlistResource(XAResource xaResource) throws RollbackException, IllegalStateException, SystemException
    {
        if (xaResource instanceof DefaultXASession)
        {
            transactionalManagerWrapper.setCurrentXaResource((DefaultXASession) xaResource);
        }
        return delegate.enlistResource(xaResource);
    }

    @Override
    public int getStatus() throws SystemException
    {
        return delegate.getStatus();
    }

    @Override
    public void registerSynchronization(Synchronization synchronization) throws RollbackException, IllegalStateException, SystemException
    {
        delegate.registerSynchronization(synchronization);
    }

    @Override
    public void rollback() throws IllegalStateException, SystemException
    {
        delegate.rollback();
    }

    @Override
    public void setRollbackOnly() throws IllegalStateException, SystemException
    {
        delegate.setRollbackOnly();
    }
}

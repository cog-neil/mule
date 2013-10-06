/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.btm.transaction;

import org.mule.api.MuleContext;
import org.mule.api.config.MuleConfiguration;
import org.mule.api.context.MuleContextAware;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.transaction.TransactionManagerFactory;
import org.mule.util.UUID;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.TransactionManager;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;

public class BitronixTransactionManagerFactory implements TransactionManagerFactory, Disposable, MuleContextAware
{

    private Map<String, String> properties = new HashMap<String, String>();

    private BitronixTransactionManager tm;
    private MuleContext muleContext;

    public BitronixTransactionManagerFactory()
    {
    }

    public synchronized TransactionManager create(MuleConfiguration config) throws Exception
    {
        if (tm == null)
        {
            String workingDirectory = muleContext.getConfiguration().getWorkingDirectory();
            String part1Filename = workingDirectory + File.separator + "tx-log" + File.separator + TransactionManagerServices.getConfiguration().getLogPart1Filename();
            String part2Filename = workingDirectory + File.separator + "tx-log" + File.separator + TransactionManagerServices.getConfiguration().getLogPart2Filename();
            TransactionManagerServices.getConfiguration().setLogPart1Filename(part1Filename);
            TransactionManagerServices.getConfiguration().setLogPart2Filename(part2Filename);
            tm = TransactionManagerServices.getTransactionManager();
        }
        return tm;
    }

    public Map<String, String> getProperties()
    {
        return properties;
    }

    public void setProperties(Map<String, String> properties)
    {
        this.properties = properties;
    }

    @Override
    public void dispose()
    {
        tm.shutdown();
        tm = null;
    }

    @Override
    public void setMuleContext(MuleContext context)
    {
        this.muleContext = context;
    }
}

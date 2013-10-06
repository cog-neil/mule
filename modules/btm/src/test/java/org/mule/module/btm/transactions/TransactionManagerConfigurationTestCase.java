/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.btm.transactions;

import org.mule.api.config.ConfigurationBuilder;
import org.mule.api.transaction.TransactionManagerFactory;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.module.btm.transaction.BitronixTransactionManagerFactory;
import org.mule.tck.AbstractTxThreadAssociationTestCase;

import org.junit.Test;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TransactionManagerConfigurationTestCase extends AbstractTxThreadAssociationTestCase
{


    @Override
    protected ConfigurationBuilder getBuilder() throws Exception
    {
        return new SpringXmlConfigurationBuilder(getConfigResources());
    }

    protected String getConfigResources()
    {
        return "btm-configuration.xml";
    }

    @Test
    public void testConfiguration()
    {
        //assertNotNull(muleContext.getTransactionManager());
        //assertTrue(muleContext.getTransactionManager().getClass().getName().compareTo("arjuna") > 0);
        
        //assertTrue(arjPropertyManager.getCoordinatorEnvironmentBean().getTxReaperTimeout() == 108000);
        //assertTrue(arjPropertyManager.getCoordinatorEnvironmentBean().getDefaultTimeout() == 47);
    }

	@Override
	protected TransactionManagerFactory getTransactionManagerFactory()
	{
		return new BitronixTransactionManagerFactory();
	}

}

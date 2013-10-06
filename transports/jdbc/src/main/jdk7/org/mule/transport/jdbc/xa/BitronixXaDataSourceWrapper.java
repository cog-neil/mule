package org.mule.transport.jdbc.xa;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

import bitronix.tm.resource.jdbc.PoolingDataSource;

/**
 *
 */
public class BitronixXaDataSourceWrapper implements DataSource
{

    private PoolingDataSource xaDataSource;

    public BitronixXaDataSourceWrapper(PoolingDataSource xaDataSource)
    {
        this.xaDataSource = xaDataSource;
    }

    public int getLoginTimeout() throws SQLException
    {
        return xaDataSource.getLoginTimeout();
    }

    public void setLoginTimeout(int seconds) throws SQLException
    {
        xaDataSource.setLoginTimeout(seconds);
    }

    public PrintWriter getLogWriter() throws SQLException
    {
        return xaDataSource.getLogWriter();
    }

    public void setLogWriter(PrintWriter out) throws SQLException
    {
        xaDataSource.setLogWriter(out);
    }

    public Connection getConnection() throws SQLException
    {
        return new BitronixConnectionWrapper(xaDataSource.getConnection());
    }

    public Connection getConnection(String username, String password) throws SQLException
    {
        return new BitronixConnectionWrapper(xaDataSource.getConnection(username, password));
    }

    /**
     * @return Returns the underlying XADataSource.
     */
    public XADataSource getXaDataSource()
    {
        return (XADataSource) xaDataSource;
    }

    /**
     * @param xads The XADataSource to set.
     */
    public void setXaDataSource(XADataSource xads)
    {
        this.xaDataSource = (PoolingDataSource) xads;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException
    {
        return false;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException
    {
        return null;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException
    {
        //return xaDataSource.getParentLogger();
        return null;
    }

}
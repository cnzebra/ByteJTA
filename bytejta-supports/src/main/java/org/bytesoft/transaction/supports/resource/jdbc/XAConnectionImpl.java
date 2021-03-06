/**
 * Copyright 2014-2017 yangming.liu<bytefox@126.com>.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, see <http://www.gnu.org/licenses/>.
 */
package org.bytesoft.transaction.supports.resource.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.ConnectionEventListener;
import javax.sql.StatementEventListener;
import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;

import org.bytesoft.bytejta.supports.resource.CommonResourceDescriptor;

public class XAConnectionImpl implements XAConnection /* , ConnectionEventListener */ {
	private String identifier;
	private XAConnection delegate;

	// public void connectionClosed(ConnectionEvent event) {}
	// public void connectionErrorOccurred(ConnectionEvent event) {}

	public Connection getConnection() throws SQLException {
		ConnectionImpl connection = new ConnectionImpl();
		Connection delegateConnection = this.delegate.getConnection();
		connection.setDelegate(delegateConnection);
		return connection;
	}

	public void close() throws SQLException {
		this.delegate.close();
	}

	public void addConnectionEventListener(ConnectionEventListener listener) {
		this.delegate.addConnectionEventListener(listener);
	}

	public void removeConnectionEventListener(ConnectionEventListener listener) {
		this.delegate.removeConnectionEventListener(listener);
	}

	public void addStatementEventListener(StatementEventListener listener) {
		this.delegate.addStatementEventListener(listener);
	}

	public void removeStatementEventListener(StatementEventListener listener) {
		this.delegate.removeStatementEventListener(listener);
	}

	public XAResource getXAResource() throws SQLException {
		XAResource xares = this.delegate.getXAResource();
		CommonResourceDescriptor descriptor = new CommonResourceDescriptor();
		descriptor.setIdentifier(this.identifier);
		descriptor.setDelegate(xares);
		return descriptor;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public XAConnection getDelegate() {
		return delegate;
	}

	public void setDelegate(XAConnection delegate) {
		this.delegate = delegate;
	}

}

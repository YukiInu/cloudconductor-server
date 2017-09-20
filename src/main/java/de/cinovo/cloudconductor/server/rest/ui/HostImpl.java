package de.cinovo.cloudconductor.server.rest.ui;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import de.cinovo.cloudconductor.api.enums.ServiceState;
import de.cinovo.cloudconductor.api.interfaces.IHost;
import de.cinovo.cloudconductor.api.model.Host;
import de.cinovo.cloudconductor.server.dao.IHostDAO;
import de.cinovo.cloudconductor.server.handler.HostHandler;
import de.cinovo.cloudconductor.server.model.EHost;
import de.cinovo.cloudconductor.server.websockets.HostDetailWSHandler;
import de.cinovo.cloudconductor.server.websockets.HostsWSHandler;
import de.cinovo.cloudconductor.server.websockets.model.WSChangeEvent;
import de.cinovo.cloudconductor.server.websockets.model.WSChangeEvent.ChangeType;
import de.taimos.dvalin.jaxrs.JaxRsComponent;
import de.taimos.restutils.RESTAssert;

/**
 * Copyright 2017 Cinovo AG<br>
 * <br>
 *
 * @author psigloch
 */
@JaxRsComponent
public class HostImpl implements IHost {
	
	@Autowired
	private IHostDAO hostDAO;
	@Autowired
	private HostHandler hostHandler;
	@Autowired
	private HostsWSHandler hostsWsHandler;
	@Autowired
	private HostDetailWSHandler hostDetailWsHandler;
	
	
	@Override
	@Transactional
	public List<Host> getHosts() {
		List<Host> result = new ArrayList<>();
		for (EHost eHost : this.hostDAO.findList()) {
			result.add(eHost.toApi());
		}
		return result;
	}
	
	@Override
	@Transactional
	public Host getHost(String hostName) {
		RESTAssert.assertNotEmpty(hostName);
		EHost eHost = this.hostDAO.findByName(hostName);
		RESTAssert.assertNotNull(eHost);
		return eHost.toApi();
	}
	
	@Override
	@Transactional
	public void deleteHost(String hostName) {
		RESTAssert.assertNotEmpty(hostName);
		EHost eHost = this.hostDAO.findByName(hostName);
		this.hostDAO.delete(eHost);
		this.hostsWsHandler.broadcastEvent(new WSChangeEvent<Host>(ChangeType.DELETED, eHost.toApi()));
	}
	
	@Override
	@Transactional
	public void setServiceState(String hostName, String serviceName, ServiceState newState) {
		RESTAssert.assertNotEmpty(hostName);
		RESTAssert.assertNotEmpty(serviceName);
		RESTAssert.assertNotNull(newState);
		EHost eHost = this.hostDAO.findByName(hostName);
		this.hostHandler.changeServiceState(eHost, serviceName, newState);
		eHost = this.hostDAO.save(eHost);
		this.hostDetailWsHandler.broadcastChange(hostName, new WSChangeEvent<Host>(ChangeType.UPDATED, eHost.toApi()));
	}
}
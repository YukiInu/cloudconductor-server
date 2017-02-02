package de.cinovo.cloudconductor.server.model;

/*
 * #%L
 * cloudconductor-server
 * %%
 * Copyright (C) 2013 - 2014 Cinovo AG
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 * #L%
 */

import de.cinovo.cloudconductor.api.enums.ServiceState;
import de.taimos.dvalin.jpa.IEntity;

import javax.persistence.*;

/**
 * Copyright 2013 Cinovo AG<br>
 * <br>
 *
 * @author psigloch
 */
@Entity
@Table(name = "servicestate", schema = "cloudconductor")
public class EServiceState implements IEntity<Long> {

	private static final long serialVersionUID = 1L;
	private Long id;

	private EService service;

	private EHost host;
	private ServiceState state = ServiceState.STOPPED;


	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the service
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "serviceid")
	public EService getService() {
		return this.service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(EService service) {
		this.service = service;
	}

	/**
	 * @return the host
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "hostid")
	public EHost getHost() {
		return this.host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(EHost host) {
		this.host = host;
	}

	/**
	 * @return the state
	 */
	public ServiceState getState() {
		return this.state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(ServiceState state) {
		this.state = state;
	}

	/**
	 * apply the next state in the chain
	 */
	public void nextState() {
		this.state = this.state.next();
	}
}

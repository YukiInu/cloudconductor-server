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
import de.cinovo.cloudconductor.api.interfaces.INamed;
import de.cinovo.cloudconductor.api.model.Host;
import de.cinovo.cloudconductor.api.model.HostIdentifier;
import de.taimos.dvalin.jpa.IEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2013 Cinovo AG<br>
 * <br>
 *
 * @author psigloch
 */
@Entity
@Table(name = "host", schema = "cloudconductor")
public class EHost extends AModelApiConvertable<Host> implements IEntity<Long>, INamed {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String description;
	private Long templateid;
	private ETemplate template;
	private Long lastSeen;
	private List<EServiceState> services = new ArrayList<>();
	private List<EPackageState> packages = new ArrayList<>();
	private Long startedUpdate;
	private boolean executedSSH = false;
	private boolean executedFiles = false;
	private boolean executedPkg = false;
	private EAgent agent;
	private String uuid;

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
	 * @return the name
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the services
	 */
	@OneToMany(mappedBy = "host", fetch = FetchType.LAZY)
	public List<EServiceState> getServices() {
		return this.services;
	}

	/**
	 * @param services the services to set
	 */
	public void setServices(List<EServiceState> services) {
		this.services = services;
	}

	/**
	 * @return the packages
	 */
	@OneToMany(mappedBy = "host", fetch = FetchType.LAZY)
	public List<EPackageState> getPackages() {
		return this.packages;
	}

	/**
	 * @param packages the packages to set
	 */
	public void setPackages(List<EPackageState> packages) {
		this.packages = packages;
	}

	/**
	 * @return the templateid
	 */
	@Column(updatable = false, insertable = false)
	public Long getTemplateid() {
		return this.templateid;
	}

	/**
	 * @param templateid the templateid to set
	 */
	public void setTemplateid(Long templateid) {
		this.templateid = templateid;
	}

	/**
	 * @return the template
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "templateid")
	public ETemplate getTemplate() {
		return this.template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(ETemplate template) {
		this.template = template;
	}

	/**
	 * @return the lastSeen
	 */
	public Long getLastSeen() {
		return this.lastSeen;
	}

	/**
	 * @param lastSeen the lastSeen to set
	 */
	public void setLastSeen(Long lastSeen) {
		this.lastSeen = lastSeen;
	}

	/**
	 * @return the onUpdate
	 */
	public Long getStartedUpdate() {
		return this.startedUpdate;
	}

	/**
	 * @param onUpdate the onUpdate to set
	 */
	public void setStartedUpdate(Long onUpdate) {
		this.startedUpdate = onUpdate;
	}

	/**
	 * @return the executedSSH
	 */
	public boolean getExecutedSSH() {
		return this.executedSSH;
	}

	/**
	 * @param executedSSH the executedSSH to set
	 */
	public void setExecutedSSH(boolean executedSSH) {
		this.executedSSH = executedSSH;
	}

	/**
	 * @return the executedFiles
	 */
	public boolean getExecutedFiles() {
		return this.executedFiles;
	}

	/**
	 * @param executedFiles the executedFiles to set
	 */
	public void setExecutedFiles(boolean executedFiles) {
		this.executedFiles = executedFiles;
	}

	/**
	 * @return the executedPkg
	 */
	public boolean getExecutedPkg() {
		return this.executedPkg;
	}

	/**
	 * @param executedPkg the executedPkg to set
	 */
	public void setExecutedPkg(boolean executedPkg) {
		this.executedPkg = executedPkg;
	}

	/**
	 * @return the Agent running on this Host
	 */
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "agentid")
	public EAgent getAgent() {
		return this.agent;
	}

	/**
	 * @param agent the agent running on this host to set
	 */
	public void setAgent(EAgent agent) {
		this.agent = agent;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return this.uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	@Transient
	public Class<Host> getApiClass() {
		return Host.class;
	}

	@Override
	@Transient
	public Host toApi() {
		Host api = super.toApi();

		Map<String, ServiceState> serviceMap = new HashMap<>();
		for(EServiceState service : this.getServices()) {
			serviceMap.put(service.getService().getName(), service.getState());
		}
		api.setServices(serviceMap);

		if(this.getAgent() != null) {
			api.setAgent(this.getAgent().getName());
		}

		Map<String, String> packageMap = new HashMap<>();
		for(EPackageState pkg : this.getPackages()) {
			packageMap.put(pkg.getVersion().getPkg().getName(), pkg.getVersion().getVersion());
		}
		api.setPackages(packageMap);

		return api;
	}

	/**
	 * @return the host identifier
	 */
	@Transient
	public HostIdentifier toHostIdentifier() {
		return new HostIdentifier(this.getName(), this.getUuid());
	}
}

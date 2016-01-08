package de.cinovo.cloudconductor.server.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.taimos.dvalin.jpa.IEntity;

/**
 * Copyright 2015 Cinovo AG<br>
 * <br>
 * 
 * @author psigloch
 *
 */
@Entity
@Table(name = "packageservergroup", schema = "cloudconductor")
public class EPackageServerGroup implements IEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	
	private String name;
	private List<EPackageServer> packageServers;
	private Long primaryServerId;
	
	
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
	 * @return the packageServers
	 */
	@OneToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER, mappedBy = "serverGroup")
	public List<EPackageServer> getPackageServers() {
		return this.packageServers;
	}
	
	/**
	 * @param packageServers the packageServers to set
	 */
	public void setPackageServers(List<EPackageServer> packageServers) {
		this.packageServers = packageServers;
	}

	public Long getPrimaryServerId() { 
		return this.primaryServerId;
	}

	public void setPrimaryServerId(Long primaryServerId) {
		this.primaryServerId = primaryServerId;
	}
	
	
}

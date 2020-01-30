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

import de.cinovo.cloudconductor.api.interfaces.INamed;
import de.cinovo.cloudconductor.api.model.ConfigFile;
import de.taimos.dvalin.jpa.IEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright 2013 Cinovo AG<br>
 * <br>
 *
 * @author psigloch
 */
@Entity
@Table(name = "file", schema = "cloudconductor")
public class EFile extends AModelApiConvertable<ConfigFile> implements IEntity<Long>, INamed {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private EPackage pkg;
	private String targetPath;
	private String owner;
	private String group;
	private String fileMode;
	private boolean isTemplate;
	private boolean isReloadable;
	private boolean isDirectory;
	private String checksum;
	private Set<String> dependentServices = new HashSet<>();
	private Set<String> templates = new HashSet<>();
	
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the pkg
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "packageid")
	public EPackage getPkg() {
		return this.pkg;
	}
	
	/**
	 * @param pkg the pkg to set
	 */
	public void setPkg(EPackage pkg) {
		this.pkg = pkg;
	}
	
	/**
	 * @return the targetPath
	 */
	public String getTargetPath() {
		return this.targetPath;
	}
	
	/**
	 * @param targetPath the targetPath to set
	 */
	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}
	
	/**
	 * @return the owner
	 */
	public String getOwner() {
		return this.owner;
	}
	
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	/**
	 * @return the group
	 */
	@Column(name = "filegroup", nullable = false)
	public String getGroup() {
		return this.group;
	}
	
	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}
	
	/**
	 * @return the fileMode
	 */
	public String getFileMode() {
		return this.fileMode;
	}
	
	/**
	 * @param fileMode the fileMode to set
	 */
	public void setFileMode(String fileMode) {
		this.fileMode = fileMode;
	}
	
	/**
	 * @return the isTemplate
	 */
	public boolean isTemplate() {
		return this.isTemplate;
	}
	
	/**
	 * @param isTemplate the isTemplate to set
	 */
	public void setTemplate(boolean isTemplate) {
		this.isTemplate = isTemplate;
	}
	
	/**
	 * @return the isReloadable
	 */
	public boolean isReloadable() {
		return this.isReloadable;
	}
	
	/**
	 * @param isReloadable the isReloadable to set
	 */
	public void setReloadable(boolean isReloadable) {
		this.isReloadable = isReloadable;
	}
	
	/**
	 * @return the isDirectory
	 */
	public boolean isDirectory() {
		return this.isDirectory;
	}
	
	/**
	 * @param isDirectory the isDirectory to set
	 */
	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	
	/**
	 * @return the checksum
	 */
	public String getChecksum() {
		return this.checksum;
	}
	
	/**
	 * @param checksum the checksum to set
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
	/**
	 * @return the dependentServices
	 */
	@ElementCollection(fetch = FetchType.LAZY, targetClass = String.class)
	@CollectionTable(schema = "cloudconductor", name = "file_services", joinColumns = {@JoinColumn(name = "file_id")})
	@Cascade(CascadeType.ALL)
	@Column(name = "service")
	public Set<String> getDependentServices() {
		return this.dependentServices;
	}
	
	/**
	 * @param dependentServices the dependentServices to set
	 */
	public void setDependentServices(Set<String> dependentServices) {
		this.dependentServices = dependentServices;
	}
	
	/**
	 * @return list of templates this file is used in
	 */
	@ElementCollection(fetch = FetchType.LAZY, targetClass = String.class)
	@CollectionTable(schema = "cloudconductor", name = "file_template", joinColumns = {@JoinColumn(name = "file_id")})
	@Cascade(CascadeType.ALL)
	@Column(name = "template")
	public Set<String> getTemplates() {
		return this.templates;
	}
	
	/**
	 * @param templates the list of templates to set
	 */
	public void setTemplates(Set<String> templates) {
		this.templates = templates;
	}
	
	/**
	 * @return the name
	 */
	@Override
	@Column(nullable = false)
	public String getName() {
		return this.name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		return (this.getName() == null) ? 0 : this.getName().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EFile)) {
			return false;
		}
		EFile other = (EFile) obj;
		return (this.getName() != null) && this.getName().equals(other.getName());
	}
	
	@Override
	@Transient
	public Class<ConfigFile> getApiClass() {
		return ConfigFile.class;
	}
	
	@Override
	@Transient
	public ConfigFile toApi() {
		ConfigFile configFile = super.toApi();
		configFile.setDependentServices(this.dependentServices);
		configFile.setTemplates(new ArrayList<>(this.templates));
		return configFile;
	}
}

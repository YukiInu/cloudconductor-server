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

import de.cinovo.cloudconductor.api.enums.DependencyType;
import de.cinovo.cloudconductor.api.interfaces.INamed;
import de.cinovo.cloudconductor.api.model.Dependency;
import de.cinovo.cloudconductor.server.util.GenericModelApiConverter;
import de.taimos.dvalin.jpa.IEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Copyright 2013 Cinovo AG<br>
 * <br>
 *
 * @author psigloch
 */
@Entity
@Table(name = "dependency", schema = "cloudconductor")
public class EDependency implements IEntity<Long>, INamed {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private DependencyType type;
	private String name;
	private String operator;
	private String version;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.getName() == null) ? 0 : this.getName().hashCode());
		result = (prime * result) + ((this.getOperator() == null) ? 0 : this.getOperator().hashCode());
		result = (prime * result) + ((this.getType() == null) ? 0 : this.getType().hashCode());
		result = (prime * result) + ((this.getVersion() == null) ? 0 : this.getVersion().hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof EDependency)) {
			return false;
		}
		EDependency other = (EDependency) obj;
		if (this.getName() == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!this.getName().equals(other.getName())) {
			return false;
		}
		if (this.getOperator() == null) {
			if (other.getOperator() != null) {
				return false;
			}
		} else if (!this.getOperator().equals(other.getOperator())) {
			return false;
		}
		if (this.getType() != other.getType()) {
			return false;
		}
		if (this.getVersion() == null) {
			if (other.getVersion() != null) {
				return false;
			}
		} else if (!this.getVersion().equals(other.getVersion())) {
			return false;
		}
		return true;
	}
	
	/**
	 * @return the id
	 */
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
	 * @return the type
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	public DependencyType getType() {
		return this.type;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(DependencyType type) {
		this.type = type;
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
	
	/**
	 * @return the operator
	 */
	@Column(nullable = false)
	public String getOperator() {
		return this.operator;
	}
	
	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	/**
	 * @return the version
	 */
	@Column(nullable = false)
	public String getVersion() {
		return this.version;
	}
	
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * @return the api object
	 */
	@Transient
	public Dependency toApi() {
		return GenericModelApiConverter.convert(this, Dependency.class);
	}
}

package de.cinovo.cloudconductor.server.dao.hibernate;

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

import org.springframework.stereotype.Repository;

import de.cinovo.cloudconductor.server.dao.IServiceStateDAO;
import de.cinovo.cloudconductor.server.model.EServiceState;
import de.taimos.dvalin.jpa.EntityDAOHibernate;

/**
 * Copyright 2013 Cinovo AG<br>
 * <br>
 * 
 * @author psigloch
 * 
 */
@Repository("ServiceStateDAOHib")
public class ServiceStateDAOHib extends EntityDAOHibernate<EServiceState, Long> implements IServiceStateDAO {
	
	@Override
	public Class<EServiceState> getEntityClass() {
		return EServiceState.class;
	}
	
	@Override
	public EServiceState findByNameAndHost(String serviceName, String hostUUID) {
		// language=HQL
		String q = "FROM EServiceState as ss JOIN FETCH ss.service AS s JOIN FETCH ss.host AS h" +
				" WHERE s.name = ?1 AND h.uuid = ?2";
		return this.findByQuery(q, serviceName, hostUUID);
	}
}

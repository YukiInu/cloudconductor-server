package de.cinovo.cloudconductor.server.dao;

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

import de.cinovo.cloudconductor.server.model.ERepo;
import de.cinovo.cloudconductor.server.model.ERepoMirror;
import de.taimos.dvalin.jpa.IEntityDAO;

import java.util.List;
import java.util.Set;

/**
 * Copyright 2013 Cinovo AG<br>
 * <br>
 * 
 * @author psigloch
 * 
 */
public interface IRepoMirrorDAO extends IEntityDAO<ERepoMirror, Long> {

	List<ERepoMirror> findByIds(Set<Long> ids);

	/**
	 * @param repo the {@link ERepo}
	 * @return all mirrors of the repo
	 */
	List<ERepoMirror> findForRepo(ERepo repo);

	/**
	 * @param repoName	repository name
	 * @return list of mirrors for given repo
	 */
	List<ERepoMirror> findForRepo(String repoName);

	/**
	 * @param repoName	repository name
	 * @return primary mirror for given repo
	 */
	ERepoMirror findPrimaryForRepo(String repoName);

	/**
	 * Delete mirrors for repo
	 *
	 * @param repo	repository
	 * @return number of deleted mirrors
	 */
	int deleteForRepo(ERepo repo);

	/**
	 * Delete mirrors for repo
	 *
	 * @param repoName	repository name
	 * @return number of deleted mirrors
	 */
	int deleteForRepo(String repoName);

}

package de.cinovo.cloudconductor.server.web.interfaces;

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

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;

import de.cinovo.cloudconductor.api.MediaType;
import de.cinovo.cloudconductor.server.util.exception.FormErrorException;
import de.cinovo.cloudconductor.server.web.helper.AjaxAnswer;
import de.taimos.cxf_renderer.model.RenderedUI;

/**
 * Copyright 2013 Cinovo AG<br>
 * <br>
 *
 * @author psigloch
 *		
 */
@Path(ITemplate.ROOT)
public interface ITemplate {
	
	/** the root */
	public static final String ROOT = "/templates";
	
	/** */
	public static final String UPDATE_PACKAGE_ACTION = "/{" + IWebPath.VAR_NAME + "}" + IWebPath.ACTION_UPDATE;
	/** */
	public static final String REMOVE_PACKAGE_ACTION = "/{" + IWebPath.VAR_NAME + "}" + IWebPath.ACTION_REMOVE;
	
	/** */
	public static final String EDIT_TEMPLATE_ACTION = "/{" + IWebPath.VAR_NAME + "}" + IWebPath.ACTION_EDIT;
	/** */
	public static final String ADD_PACKAGE_ACTION = "/{" + IWebPath.VAR_NAME + "}/package" + IWebPath.ACTION_ADD;
	/** */
	public static final String DELETE_TEMPLATE_ACTION = "/{" + IWebPath.VAR_NAME + "}" + IWebPath.ACTION_DELETE;
	
	/** */
	public static final String DEFAULT_SERVICE_STATE = "/{" + IWebPath.VAR_NAME + "}/services/default";
	
	/** */
	public static final String EDIT_TEMPLATE_AGENT_CONFIG_ACTION = "/{" + IWebPath.VAR_NAME + "}/agentconfig" + IWebPath.ACTION_EDIT;
	
	
	/**
	 * @return the view
	 */
	@GET
	@Path(IWebPath.DEFAULTVIEW)
	@Produces(MediaType.TEXT_HTML)
	public abstract RenderedUI view();
	
	/**
	 * @param tname the template name
	 * @param updatePackages the package names to update
	 * @return an ajax answer
	 */
	@POST
	@Path(ITemplate.UPDATE_PACKAGE_ACTION)
	@Produces(MediaType.APPLICATION_JSON)
	public abstract AjaxAnswer updatePackages(@PathParam(IWebPath.VAR_NAME) String tname, @FormParam("updatePackage") List<String> updatePackages);
	
	/**
	 * @param tname the template name
	 * @param deletePackages the package names to delete
	 * @return an ajax answer
	 */
	@POST
	@Path(ITemplate.REMOVE_PACKAGE_ACTION)
	@Produces(MediaType.APPLICATION_JSON)
	public abstract AjaxAnswer changeTemplateState(@PathParam(IWebPath.VAR_NAME) String tname, @FormParam("deletePackage") List<String> deletePackages);
	
	/**
	 * @return the view
	 */
	@GET
	@Path(IWebPath.ACTION_ADD)
	@Produces(MediaType.TEXT_HTML)
	public abstract RenderedUI addTemplateView();
	
	/**
	 * @param templatename the template name
	 * @param packageManager the package manager
	 * @param description the template description
	 * @param autoupdate the auto update flag
	 * @param smoothupdate the smooth update flag
	 * @return an ajax answer
	 * @throws FormErrorException on form errors
	 */
	@POST
	@Path(IWebPath.ACTION_ADD)
	@Produces(MediaType.APPLICATION_JSON)
	public abstract AjaxAnswer addTemplate(@FormParam("templatename") String templatename, @FormParam("packageManager") Long packageManager, @FormParam("description") String description, @FormParam("autoupdate") String autoupdate, @FormParam("smoothupdate") String smoothupdate) throws FormErrorException;
	
	/**
	 * @param tname the tempalte name
	 * @return the modal content
	 */
	@GET
	@Path(ITemplate.EDIT_TEMPLATE_ACTION)
	@Produces(MediaType.TEXT_HTML)
	public abstract RenderedUI editTemplateView(@PathParam(IWebPath.VAR_NAME) String tname);
	
	/**
	 * @param tname the old template name
	 * @param templatename the new template name
	 * @param packageManagerId the package manager id
	 * @param description the template description
	 * @param autoupdate the auto update flag
	 * @param smoothupdate the smooth update flag
	 * @return an ajax answer
	 * @throws FormErrorException on error
	 */
	@POST
	@Path(ITemplate.EDIT_TEMPLATE_ACTION)
	@Produces(MediaType.APPLICATION_JSON)
	public abstract AjaxAnswer editTemplate(@PathParam(IWebPath.VAR_NAME) String tname, @FormParam("templatename") String templatename, @FormParam("packageManager") Long packageManagerId, @FormParam("description") String description, @FormParam("autoupdate") String autoupdate, @FormParam("smoothupdate") String smoothupdate) throws FormErrorException;
	
	/**
	 * @param tname the template name
	 * @return the modal content
	 */
	@GET
	@Path(ITemplate.ADD_PACKAGE_ACTION)
	@Produces(MediaType.TEXT_HTML)
	public abstract RenderedUI addPackageView(@PathParam(IWebPath.VAR_NAME) String tname);
	
	/**
	 * @param tname the template name
	 * @param pkgs the package names
	 * @return an ajax answer
	 * @throws FormErrorException on form errors
	 */
	@POST
	@Path(ITemplate.ADD_PACKAGE_ACTION)
	@Produces(MediaType.APPLICATION_JSON)
	public abstract AjaxAnswer addPackage(@PathParam(IWebPath.VAR_NAME) String tname, @FormParam("pkg") String[] pkgs) throws FormErrorException;
	
	/**
	 * @param tname the package names
	 * @return the modal content
	 */
	@GET
	@Path(ITemplate.DELETE_TEMPLATE_ACTION)
	@Produces(MediaType.TEXT_HTML)
	public abstract RenderedUI deleteTemplateView(@PathParam(IWebPath.VAR_NAME) String tname);
	
	/**
	 * @param tname the template name
	 * @return an ajax answer
	 * @throws FormErrorException on form errors
	 */
	@POST
	@Path(ITemplate.DELETE_TEMPLATE_ACTION)
	@Produces(MediaType.APPLICATION_JSON)
	public abstract AjaxAnswer deleteTemplate(@PathParam(IWebPath.VAR_NAME) String tname) throws FormErrorException;
	
	/**
	 * @param tname the template name
	 * @return the modal content
	 */
	@GET
	@Path(ITemplate.DEFAULT_SERVICE_STATE)
	@Produces(MediaType.TEXT_HTML)
	public abstract RenderedUI defaultServiceStatesView(@PathParam(IWebPath.VAR_NAME) String tname);
	
	/**
	 * @param tname the template name
	 * @param startService service names to start
	 * @param stopService service names to stop
	 * @return an ajax answer
	 */
	@POST
	@Path(ITemplate.DEFAULT_SERVICE_STATE)
	@Produces(MediaType.APPLICATION_JSON)
	public abstract AjaxAnswer changeDefaultServiceStates(@PathParam(IWebPath.VAR_NAME) String tname, @FormParam("startService") List<String> startService, @FormParam("stopService") List<String> stopService);
	
	/**
	 * @param tname the template name
	 * @return the modal content
	 */
	@GET
	@Path(ITemplate.EDIT_TEMPLATE_AGENT_CONFIG_ACTION)
	@Produces(MediaType.TEXT_HTML)
	public abstract RenderedUI editTemplateAgentConfigView(@PathParam(IWebPath.VAR_NAME) String tname);
	
	/**
	 * @param tname the old template name
	 * @param aliveTimer alive timer
	 * @param aliveTimerUnit alive timer unit
	 * @param doSshKeys do ssh keys
	 * @param sshKeysTimer ssh key timer
	 * @param sshKeysTimerUnit ssh key timer unit
	 * @param doPackageManagement do package management
	 * @param packageManagementTimer package management timer
	 * @param packageManagementTimerUnit package management timer unit
	 * @param doFileManagement do file management
	 * @param fileManagementTimer file management timer
	 * @param fileManagementTimerUnit file management timer unit
	 * @return an ajax answer
	 * @throws FormErrorException on error
	 */
	@POST
	@Path(ITemplate.EDIT_TEMPLATE_AGENT_CONFIG_ACTION)
	@Produces(MediaType.APPLICATION_JSON)
	public abstract AjaxAnswer editTemplateAgentConfig(@PathParam(IWebPath.VAR_NAME) String tname, MultivaluedMap<String, String> form) throws FormErrorException;
	
}

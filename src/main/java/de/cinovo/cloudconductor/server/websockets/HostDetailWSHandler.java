package de.cinovo.cloudconductor.server.websockets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.cinovo.cloudconductor.api.model.Host;
import de.cinovo.cloudconductor.server.websockets.model.WSChangeEvent;

/**
 * 
 * Copyright 2017 Cinovo AG<br>
 * <br>
 * 
 * @author mweise
 *
 */
@Service
public class HostDetailWSHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HostDetailWSHandler.class);
	private Map<String, List<HostDetailWSAdapter>> connectedSockets = new HashMap<>();
	
	
	/**
	 * 
	 * @param hostName the name of the host for which a WS connection should be added
	 * @param wsAdapter the adapter for the WS connection
	 */
	public void addSocket(String hostName, HostDetailWSAdapter wsAdapter) {
		if (this.connectedSockets.containsKey(hostName)) {
			List<HostDetailWSAdapter> list = this.connectedSockets.get(hostName);
			list.add(wsAdapter);
			HostDetailWSHandler.LOGGER.info("Added new WebSocket for {}", hostName);
		} else {
			List<HostDetailWSAdapter> newList = new ArrayList<>();
			newList.add(wsAdapter);
			this.connectedSockets.put(hostName, newList);
			HostDetailWSHandler.LOGGER.info("Added new WebSocket for {}", hostName);
		}
	}
	
	/**
	 * 
	 * @param hostName the name of the host for which a WS connection should be removed
	 * @param adapter the adapter for the WS connection to be removed
	 * @return true if removal was successful, false otherwise
	 */
	public boolean removeSocket(String hostName, HostDetailWSAdapter adapter) {
		if (this.connectedSockets.containsKey(hostName)) {
			List<HostDetailWSAdapter> list = this.connectedSockets.get(hostName);
			return list.remove(adapter);
		}
		
		HostDetailWSHandler.LOGGER.error("Unable to remove socket, socket for {} does not exist!", hostName);
		return false;
	}
	
	/**
	 * 
	 * @param hostName the host which has changed
	 * @param event the event to be broadcasted
	 */
	public void broadcastChange(String hostName, WSChangeEvent<Host> event) {
		List<HostDetailWSAdapter> list = this.connectedSockets.get(hostName);
		
		if (list != null) {
			for (HostDetailWSAdapter adapter : list) {
				adapter.sendChangeEvent(event);
			}
		}
	}
}
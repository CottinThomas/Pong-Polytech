package com.polytech.pong;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.polytech.pong.network.Client;
import com.polytech.pong.network.IServerStatus;
import com.polytech.pong.network.NetObject;
import com.polytech.pong.network.Server;

public class ServerHandler {

	private NetObject network;
	private boolean serverConnected;
	private boolean serverHost;
	
	private List<IServerEvent> serverEventListener;
	// TODO : add abstract server attribute
	
	// TODO : Complete class with server implementation	
	
	public ServerHandler(Application application) {
		serverEventListener = new ArrayList<>();
		serverConnected = false;
		serverHost = true;
	}
	
	/**
	 * Create host server
	 * @return server host ip
	 */
	public String createServer()
	{
		serverHost = true;
		network = new Server();
		network.start();
		
		subscribeServerStatus();
		
		
		
		String hostIP = null;
		try {
			hostIP = Inet4Address.getLocalHost().getHostAddress();
			System.err.println(hostIP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hostIP;
	}
	
	/**
	 * Create slave server
	 * @param hostIp server to join
	 */
	public void createServer(String hostIp)
	{
		serverHost = false;
		network = new Client(hostIp, Server.PORT);
		network.start();
		subscribeServerStatus();
	}
	
	public boolean isServerConnected()
	{
		return serverConnected;
	}
	
	public boolean isServerHost()
	{
		return serverHost;
	}
	
	public NetObject getServer()
	{
		return network;
	}
	
	public void stopServer()
	{
		network.interrupt();
	}
	
	public void addServerEvent(IServerEvent listener)
	{
		serverEventListener.add(listener);
	}
	
	public void removeServerEvent(IServerEvent listener)
	{
		serverEventListener.remove(listener);
	}
	
	private void subscribeServerStatus()
	{
		network.addServerStatusListener(new IServerStatus() {
			
			@Override
			public void notifyServerStatus(EServerStatus status) {
				//System.out.println("SH : CONNEXION OK");
				List<IServerEvent> copy = new ArrayList<>(serverEventListener);
				for(IServerEvent listener : copy)
				{
					listener.notifyServerStatus(status);
				}
			}
			
			@Override
			public void notifyMessageReceived(Object message) {
				//System.out.println("SH : MESSAGE RECEIVED");
				List<IServerEvent> copy = new ArrayList<>(serverEventListener);
				for(IServerEvent listener : copy)
				{
					listener.notifyMessageReceived(message);
				}
			}
		});
	}
}

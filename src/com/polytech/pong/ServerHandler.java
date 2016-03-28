package com.polytech.pong;

public class ServerHandler {

	private Application application;
	private boolean serverConnected;
	private boolean serverHost;
	// TODO : add abstract server attribute
	
	// TODO : Complete class with server implementation	
	
	public ServerHandler(Application application) {
		this.application = application;
		serverConnected = false;
		serverHost = true;
	}
	
	/**
	 * Create host server
	 * @return server host ip
	 */
	public String createServer()
	{
		subscribeServerStatus();
		return "HOST_IP_NOT_IMPLEMENTED";
	}
	
	/**
	 * Create slave server
	 * @param hostIp server to join
	 */
	public void createServer(String hostIp)
	{
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
	
	public void stopServer()
	{
		
	}
	
	private void subscribeServerStatus()
	{
		
	}
}

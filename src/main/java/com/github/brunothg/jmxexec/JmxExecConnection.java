package com.github.brunothg.jmxexec;

public class JmxExecConnection {
    private String ip;
    private int port;
    private String mbean;
    private String method;

    public JmxExecConnection() {
	setIp(System.getProperty("java.rmi.server.hostname"));

	final String port = System.getProperty("com.sun.management.jmxremote.port");
	if (port != null) {
	    try {
		setPort(Integer.valueOf(port));
	    } catch (final NumberFormatException e) {
	    }
	}
    }

    /**
     * @return the ip
     */
    public String getIp() {
	return this.ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
	this.ip = ip;
    }

    /**
     * @return the port
     */
    public int getPort() {
	return this.port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
	this.port = port;
    }

    /**
     * @return the mbean
     */
    public String getMbean() {
	return this.mbean;
    }

    /**
     * @param mbean the mbean to set
     */
    public void setMbean(String mbean) {
	this.mbean = mbean;
    }

    /**
     * @return the method
     */
    public String getMethod() {
	return this.method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
	this.method = method;
    }

}

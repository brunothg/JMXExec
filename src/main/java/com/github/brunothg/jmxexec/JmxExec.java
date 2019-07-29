package com.github.brunothg.jmxexec;

import java.io.IOException;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JmxExec {

    public static JMXConnector createConnection(int port, String server) throws IOException {
	final String serviceUrlString = "service:jmx:rmi:///jndi/rmi://" + server + ":" + port + "/jmxrmi";
	final JMXServiceURL serviceUrl = new JMXServiceURL(serviceUrlString);
	final JMXConnector connector = JMXConnectorFactory.connect(serviceUrl);

	return connector;
    }

    public static void exec(JmxExecConnection con) throws IOException, MalformedObjectNameException,
	    InstanceNotFoundException, MBeanException, ReflectionException {
	final JMXConnector jmxConnector = createConnection(con.getPort(), con.getIp());
	final MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();
	final ObjectName mbean = new ObjectName(con.getMbean());

	System.out.println(
		"Exec " + con.getMethod() + " on " + con.getMbean() + " at " + con.getIp() + ":" + con.getPort());
	connection.invoke(mbean, con.getMethod(), null, null);
    }
}

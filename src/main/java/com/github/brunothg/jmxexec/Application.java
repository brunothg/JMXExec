package com.github.brunothg.jmxexec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * -Dcom.sun.management.jmxremote=true <br/>
 * -Dcom.sun.management.jmxremote.port=6001 <br/>
 * -Dcom.sun.management.jmxremote.authenticate=false <br/>
 * -Dcom.sun.management.jmxremote.ssl=false <br/>
 * -Djava.rmi.server.hostname=127.0.0.1 <br/>
 * -Dcom.sun.management.jmxremote.rmi.port=6001 <br/>
 * 
 * @author bruns_m
 *
 */
public class Application {
    public static final String OPTION_MBEAN = "mb";
    public static final String OPTION_METHOD = "m";
    public static final String OPTION_PORT = "p";
    public static final String OPTION_IP = "i";
    public static final String OPTION_FILE = "f";

    public static void main(String[] args) {
	try {
	    cmd(args);
	} catch (final Exception e) {
	    e.printStackTrace();
	}
    }

    private static void cmd(String[] args) throws ParseException, IOException {
	final CommandLineParser parser = new DefaultParser();
	final CommandLine cmd = parser.parse(cmdOptions(), args);

	final String mbean = cmd.getOptionValue(OPTION_MBEAN);
	final String method = cmd.getOptionValue(OPTION_METHOD);
	final Integer port = toInt(cmd.getOptionValue(OPTION_PORT));
	final String ip = cmd.getOptionValue(OPTION_IP);

	final String propertiesFile = cmd.getOptionValue(OPTION_FILE);
	if (propertiesFile != null) {
	    final List<String> newArgs = Files.readAllLines(Paths.get(propertiesFile), StandardCharsets.UTF_8);
	    cmd(newArgs.toArray(new String[0]));
	    return;
	}

	if (mbean == null || method == null || port == null || ip == null) {
	    System.err.println("Missing options.");
	    return;
	}
	try {
	    final JmxExecConnection con = new JmxExecConnection();
	    con.setIp(ip);
	    con.setPort(port);
	    con.setMbean(mbean);
	    con.setMethod(method);

	    JmxExec.exec(con);
	} catch (final Exception e) {
	    e.printStackTrace();
	}
    }

    private static Integer toInt(String value) {
	if (value == null) {
	    return null;
	}
	return Integer.valueOf(value);
    }

    private static Options cmdOptions() {
	final Options options = new Options();
	options.addOption(OPTION_MBEAN, "mbean", true,
		"MBean-Name e.g. org.springframework.boot:type=Admin,name=SpringApplication");

	options.addOption(OPTION_METHOD, "method", true, "Method to invocate on MBean");

	options.addOption(OPTION_PORT, "port", true, "JMX Port");

	options.addOption(OPTION_IP, "ip", true, "JMX IP");

	options.addOption(OPTION_FILE, "file", true, "JMX Connection properties in file");

	return options;
    }
}

package com.ossnms.bicnet.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ApplicationProperties {
	
	private static final Logger logger = Logger.getLogger(ApplicationProperties.class);

    private static HashMap instanceMap = new HashMap(97);
    
    private Properties props = new Properties();
    private String propertyFile = "";

    protected ApplicationProperties(String resourceName) {
        propertyFile = resourceName;
        loadProperties(resourceName);
    }

	protected ApplicationProperties(InputStream in) {
    	loadProperties(in);
	}

    static public ApplicationProperties getInstance(String resourceName) {
    	
        if (instanceMap.containsKey(resourceName)) {
            return ((ApplicationProperties) instanceMap.get(resourceName));
        } else {
            ApplicationProperties ap = new ApplicationProperties(resourceName);
            instanceMap.put(resourceName, ap);
            return ap;
        }
    }

	static public ApplicationProperties getInstance(String resourceName, InputStream in) {
    	
		if (instanceMap.containsKey(resourceName)) {
			return ((ApplicationProperties) instanceMap.get(resourceName));
		} else {
			ApplicationProperties ap = new ApplicationProperties(in);
			instanceMap.put(resourceName, ap);
			return ap;
		}
	}

    public Enumeration getPropertyNames() {
        return props.propertyNames();
    }

    public String getProperty(String name) {
        return props.getProperty(name);
    }

    public String getProperty(String name, String defaultValue) {
        return props.getProperty(name, defaultValue);
    }

    public Properties getProperties() {
        return props;
    }

	public String toString() {
		StringBuffer sb = new StringBuffer(4096);
		Enumeration e = getPropertyNames();
		while (e.hasMoreElements()) {
			String name = e.nextElement().toString();
			sb.append(name + " = " + getProperty(name) + "\n");
		}
		return sb.toString();
	}

    protected void loadProperties(String resourceName) {
        try {
            InputStream in = getClass().getResourceAsStream(resourceName);
            if (null == in)
                in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
            if (in != null) {
                props.load(in);
				logger.debug("Successfully loaded properties from " + resourceName);
            }
        } catch (IOException e) {
        	logger.warn("Could not load properties from " + resourceName);
            throw new RuntimeException("Failed to load properties from " + resourceName, e);
        }
    }

	protected void loadProperties(InputStream in) {
		try {
			props.load(in);
			logger.debug("Successfully loaded properties from " + in);
		} catch (IOException ioe) {
			logger.warn("Could not load properties from " + in);
			throw new RuntimeException("Failed to load properties from input stream", ioe);
		}
	}


//	public static void setLogger(Logger l) {
//		if (l == null)
//			logger = Logger.getLogger(ApplicationProperties.class);
//		else
//			logger = l;
//	}

	public static final Logger getLogger() {
		return logger;
	}

}

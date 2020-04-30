package com.zwj.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

public class PropertiesUtil {
    private static final String CONFIGNAME = "application.properties";
    private static Properties props = new Properties();

    public PropertiesUtil(String fileName) {
        props = getProperties(fileName);
    }

    public static Properties getProperties() {
        return getProperties("application.properties");
    }

    public static Properties getProperties(String fileName) {
        try {
            InputStream inStream = PropertiesUtil.class.getResourceAsStream("/" + fileName);
            props.load(inStream);
            Iterator i$ = props.entrySet().iterator();

            while(i$.hasNext()) {
                Entry<Object, Object> entry = (Entry)i$.next();
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return props;
    }

    public static String getProperty(String key) {
        if (props == null) {
            props = getProperties();
        }

        return props.getProperty(key).trim();
    }

    public static void setProperty(String key, String value) {
        props.setProperty(key, value);
    }
}

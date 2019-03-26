package com.excilys.cdb.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    /**
     * prop Properties.
     */
    Properties prop;

    /**
     * Constructor.
     */
    public PropertyReader() {
        prop = new Properties();

        try {
            InputStream inputStream = PropertyReader.class.getResourceAsStream("/config.properties");
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * Returns the value of the specified property.
     *
     * @param key String
     * @return String
     */
    public String get(String key) {
        return prop.getProperty(key);
    }
}

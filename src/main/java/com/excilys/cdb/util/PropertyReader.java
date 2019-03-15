package com.excilys.cdb.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

	/**
	 * prop Properties
	 */
	Properties prop;

	/**
	 * Constructor
	 */
	public PropertyReader() {
		this.prop = new Properties();

		try {
			FileInputStream inputStream = new FileInputStream("src/main/resources/config.properties");
			this.prop.load(inputStream);
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
		return this.prop.getProperty(key);
	}
}

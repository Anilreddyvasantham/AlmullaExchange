package com.amg.exchange.dbconfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.Properties;

public class DBConnection {

	public static Connection getdataconnection() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
		Connection con = null;
		try {
			
			Properties prop = new Properties();
			InputStream input = null;
		 
			try {
				System.out.println(new GettingDBConfiguration().getPath());
				input = new FileInputStream(new GettingDBConfiguration().getPath());
		 
				// load a properties file
				prop.load(input);
		 
				// get the property value and print it out
				String driver = prop.getProperty("driver"); 
				String url = prop.getProperty("url");  
				String username = prop.getProperty("username");
				String password = prop.getProperty("password");
				
				Class.forName(driver); // load Oracle driver
				Connection conn = DriverManager.getConnection(url, username,password);
				return conn;
				
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		} catch (SQLException sql) {
			sql.printStackTrace();
		} catch (MissingResourceException res) {
			res.printStackTrace();
		}
		return con;
	}

	public static void main(String arg[]) throws Exception {
		DBConnection.getdataconnection();
	}
	
}

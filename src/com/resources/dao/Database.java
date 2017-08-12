package com.resources.dao;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

public class Database {

	public Connection getConnection() throws Exception {
		try {
			String connectionURL = "jdbc:mysql://localhost:3306/phonebook";
			Connection connection = null;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = (Connection) DriverManager.getConnection(connectionURL, "root", "anu123");
			return connection;
		}catch(Exception e) {
			throw e;
		}



	}

}

package com.log.aggregation.tests;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogAggregationsTests {

	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException, IOException {
		Class.forName("org.hsqldb.jdbc.JDBCDriver");

		
		try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
			
		}
	}



	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/logdb", "sa", "");
	}

	@Test
	public void getrecord() {

	}

	@Test
	public void checkRecordExists() {

	}
}

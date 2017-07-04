package com.dhakre.rohit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

	public Connection con = null;

	public DB() {
		System.out.println("Establishing Connection");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
			System.out.println("Connected");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ResultSet runExecuteQuery(String url) throws SQLException {
		Statement stmt = con.createStatement();
		return stmt.executeQuery(url);
	}

	public boolean runExecute(String url) throws SQLException {
		Statement stmt = con.createStatement();
		return stmt.execute(url);
	}

	@Override
	protected void finalize() throws Throwable {
		if (!con.isClosed() || con != null) {
			con.close();
		}
	}

}

package util;

import java.sql.*;

public class Connect {
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "clminton";
	private final String HOST = "localhost:3306";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	private Connection con;
	private Statement statement;
	private ResultSet resultSet;
	
	private static Connect instance;
	
	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			statement = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized Connect getInstance() {
		if (instance == null)
			instance = new Connect();
		
		return instance;
	}
	
	public ResultSet execQuery(String query) {
		ResultSet res = null;
		try {
			res = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public void execUpdate(String query) {
		try {
			statement.executeUpdate(query);
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
		}
	}
	
	public boolean exec(String query) {
		boolean exist = false;
		try {
			exist = statement.execute(query);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return exist;
	}
	
	public PreparedStatement createPreparedStatement(String query) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(query);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ps;
	}
	
}

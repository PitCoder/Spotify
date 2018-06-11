package services;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;


public class DatabaseConnectionService {
	private String dbUrl;		
	private String username; //My default db username is admin
	private String password; //My default db password is admin
	
	public DatabaseConnectionService(){
		
		this.dbUrl = "jdbc:mysql://localhost:3306/spotify";
		//this.dbUrl = "jdbc:mysql://localhost:3306/spotify?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		this.username = "admin";
		this.password = "admin";
	}
	
	public DatabaseConnectionService(String url, String username, String password) {
		this.dbUrl = url;
		this.username = username;
		this.password = password;
	}
	
	public Connection getConnection() throws SQLException{
		System.out.println("Getting Connection to database...");
		return DriverManager.getConnection(dbUrl, username, password);
	}
}

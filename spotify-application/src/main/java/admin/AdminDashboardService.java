package admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;

import java.lang.reflect.Field;

import services.DatabaseConnectionService;

public class AdminDashboardService {
	public AdminDashboardService() {
		
	}
	
	public ArrayList<String> getAvailableDatabases() {
		ArrayList<String> availableDatabases = new ArrayList<>();
		
		try {
			DatabaseConnectionService dbConnection = new DatabaseConnectionService("jdbc:mysql://localhost:3306","admin","admin");
			Connection connection = dbConnection.getConnection();
			
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet resultSet = meta.getCatalogs();
			
			while(resultSet.next()) {
				availableDatabases.add(resultSet.getString("TABLE_CAT"));
			}
			
			connection.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		
		return availableDatabases;
	}
	
	public ArrayList<String> getDatabaseTables(String database){
		ArrayList<String> databaseTables = new ArrayList<>();
		
		try {
			DatabaseConnectionService dbConnection = new DatabaseConnectionService("jdbc:mysql://localhost:3306" + "/" + database,"admin","admin");
			Connection connection = dbConnection.getConnection();
			
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet resultSet = meta.getTables(null, null, "%", null);
			
			while(resultSet.next()) {
				databaseTables.add(resultSet.getString(3));
			}
			
			connection.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		
		return databaseTables;
	}
	
	public ArrayList<ArrayList<String>> getTableContent(String database, String table){
		ArrayList<ArrayList<String>> tableContent = new ArrayList<ArrayList<String>>();
		
		try {
			DatabaseConnectionService dbConnection = new DatabaseConnectionService("jdbc:mysql://localhost:3306" + "/" + database,"admin","admin");
			Connection connection = dbConnection.getConnection();
			
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet resultSet = meta.getColumns(null, null, table, null);
			Map<Integer, String> jdbcMappings = getAllJdbcTypeNames();
			ArrayList<String> row = new ArrayList<>();
			
			//String columnName;
			//String dataType;
			//String columnSize;
			//String decimalDigits;
			//String isNullable;
			//String isAutoIncrement;
			
			row.add("COLUMN_NAME");
			row.add("DATA_TYPE");
			row.add("COLUMN_SIZE");
			row.add("DECIMAL_DIGITS");
			row.add("IS_NULLABLE");
			row.add("IS_AUTOINCREMENT");
			
			tableContent.add(row);
			
			while(resultSet.next()) {
				row = new ArrayList<>();
				
				//columnName 		= resultSet.getString("COLUMN_NAME");
				//dataType 		= resultSet.getString("DATA_TYPE");
				//columnSize 		= resultSet.getString("COLUMN_SIZE");
				//decimalDigits 	= resultSet.getString("DECIMAL_DIGITS");
				//isNullable 		= resultSet.getString("IS_NULLABLE");
				//isAutoIncrement = resultSet.getString("IS_AUTOINCREMENT");
				
				row.add(resultSet.getString("COLUMN_NAME"));
				row.add(jdbcMappings.get(resultSet.getInt("DATA_TYPE")));
				row.add(resultSet.getString("COLUMN_SIZE"));
				row.add(resultSet.getString("DECIMAL_DIGITS"));
				row.add(resultSet.getString("IS_NULLABLE"));
				row.add(resultSet.getString("IS_AUTOINCREMENT"));
				
				tableContent.add(row);
			}
			
			connection.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		
		return tableContent;
	}
	
	public void getTableData(ArrayList<ArrayList<String>> tableSet) {
		String database = tableSet.get(0).get(0);
		
		try {
			DatabaseConnectionService dbConnection = new DatabaseConnectionService("jdbc:mysql://localhost:3306" + "/" + database,"admin","admin");
			Connection connection = dbConnection.getConnection();
			
			
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
	}
	
	public Map<Integer, String> getAllJdbcTypeNames() {

	    Map<Integer, String> result = new HashMap<Integer, String>();

	    for (Field field : Types.class.getFields()) {
	        try {
				result.put((Integer)field.get(null), field.getName());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
	    }

	    return result;
	}
}

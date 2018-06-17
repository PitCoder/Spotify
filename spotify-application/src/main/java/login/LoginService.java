package login;

import services.DatabaseConnectionService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {
	private DatabaseConnectionService dbConnection;
	private String userRole = "";
	private String userName = "";
	
	public LoginService(){
		this.dbConnection = new DatabaseConnectionService();
	}
	
	public boolean isUserValid(String userId, String password){
		boolean valid = false;
		
		try{
			Connection connection = dbConnection.getConnection();
			String queryString = 
					"select r.description, u.usrname " +
					"from role r, login l, user u " +
					"where r.idRole = l.idRole " + 
					"and l.email = u.email " +
					"and l.email = ? " +
					"and l.password = ? ";
			
			PreparedStatement queryUsers = null;
			ResultSet result;
			
			queryUsers = connection.prepareStatement(queryString);
			queryUsers.setString(1,userId);
			queryUsers.setString(2, password);
			result = queryUsers.executeQuery();
			
			
			if(result.isBeforeFirst()) {
				while(result.next()){
					this.userRole = result.getString("description");
					this.userName = result.getString("usrname");
					System.out.println("Username: " + this.userName);
					System.out.println("Role: " + this.userRole);
				}
				valid = true;
			}
			else {
				System.out.println("Cursor is not before the first record or there are no rows in Result Set");
			}
			connection.close();
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		
		return valid;
	}
	
	public String getRole() {
		return this.userRole;
	}
	
	public String getUserName() {
		return this.userName;
	}
}

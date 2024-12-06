package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.management.relation.Role;

import Config.MysqlConfig;
import entity.LoginEntity;

public class LoginRepository {
	public List<LoginEntity> findByEmailAndPassword(String email, String password){
		List<LoginEntity> list = new ArrayList<LoginEntity>();

		  String query = "SELECT u.*, r.name AS role_name " +
                  "FROM users u " +
                  "JOIN roles r ON r.id = u.role_id " +
                  "WHERE u.email = ? AND u.password = ?";

		
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				LoginEntity loginEntity = new LoginEntity();
				loginEntity.setEmail(result.getString("email"));
				loginEntity.setPassword(result.getString("password"));
				loginEntity.setRoleName(result.getString("role_name"));
				loginEntity.setFullName(result.getString("fullname"));
				loginEntity.setId(result.getInt("id"));
				list.add(loginEntity);
				
			}
			
		} catch (Exception e) {
			 System.out.println("findAll : " + e.getLocalizedMessage());
		}
		
		return list;
	}
	
	public List<LoginEntity> findAll() {
		List<LoginEntity> list = new ArrayList<LoginEntity>();
		String query = "SELECT u.id, u.fullname, u.email, r.name FROM users u JOIN roles r ON r.id = u.role_id ";
		
		return list;
	}
}

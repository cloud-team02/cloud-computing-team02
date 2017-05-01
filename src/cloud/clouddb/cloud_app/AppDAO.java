package cloud.clouddb.cloud_app;

import cloud.clouddb.ConnectionDAO;

import java.sql.*;
import java.util.*;

import org.omg.CORBA.Request;

public class AppDAO {
	// false-> no same name, true-> have same name
	public boolean isExistedName(String app_name){
		Connection connection  = this.connect.getConnection();
		String sql = "select * from Apps where app_name = ?";
		try{
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, app_name);
			ResultSet results = statement.executeQuery();
			while(results.next()){
				return true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			connect.closeConnection(connection);
		}
		
		return false;
	}	
	
	
	public Collection<App> getAllApps(){
		this.searchApps();
		return this.app_collection;
	}
	
	private void searchApps(){
		Connection connection = this.connect.getConnection();
		String sql_query = "Select * from Apps";
		try {
			PreparedStatement statement = connection.prepareStatement(sql_query);
			ResultSet results = statement.executeQuery();
			
			while(results.next()){
				App new_app = new App();
				new_app.setApp_id(results.getInt("id"));
				new_app.setApp_name(results.getString("app_name"));
				new_app.setDeveloper_id(results.getInt("developer_id"));
				new_app.setUrl(results.getString("url"));
				new_app.setPrice(results.getDouble("price"));
				new_app.setUse_times(results.getInt("use_times"));
				new_app.setDescription(results.getString("description"));
				new_app.setIconUrl(results.getString("iconeUrl"));
				this.app_collection.add(new_app);
				//results.next();
				}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			connect.closeConnection(connection);
		}
	}
	
	public void addApp(App app){
		Connection connection = this.connect.getConnection();
		String sql_query = "Insert into Apps (app_name, developer_id, url,price,use_times,description,iconUrl) values (?,?,?,?,?,?,?)";
		try {
			PreparedStatement statement = connection.prepareStatement(sql_query);
			statement.setString(1, app.getApp_name());
			statement.setInt(2, app.getDeveloper_id());
			statement.setString(3, app.getUrl());
			statement.setDouble(4, app.getPrice());
			statement.setInt(5, app.getUse_times());
			statement.setString(6, app.getDescription());
			statement.setString(7, app.getIconUrl());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			connect.closeConnection(connection);
		}
	}
	
	public void deleteApp(int id){
		Connection connection = this.connect.getConnection();
		String sql_query = "Delete from Apps where id = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql_query);
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			connect.closeConnection(connection);
		}	
	}
	
	private ConnectionDAO connect = new ConnectionDAO();
	private Collection<App> app_collection = new ArrayList<App>();
		
}


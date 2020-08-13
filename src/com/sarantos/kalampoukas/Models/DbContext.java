package com.sarantos.kalampoukas.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbContext {
	Connection conn = null;
	PreparedStatement stmt = null;
	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	final String DB_URL = "jdbc:mysql://localhost/hotel_app";
	final String USER = "root";
	final String PASS = "root";
	
//opening connection	
	public DbContext() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
	}
	
	public User findUserById(int id) throws SQLException {
		connect();
		String sql = "SELECT * FROM Users WHERE id=?";
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet result = stmt.executeQuery();
		dispose();		
		if (result.next()) {
			return new User(result.getInt("id"), result.getString("name"),
					result.getString("surname"), result.getString("email"), result.getString("mobile"),
					result.getInt("role_id"), result.getString("password_hash"));
		}
		dispose();	
		return null;
	}
	
	public User findUserByEmail(String email) throws SQLException {
		connect();
		String sql = "SELECT * FROM Users WHERE email=?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, email);
		ResultSet result = stmt.executeQuery();
			
		if (result.next()) {
			return new User(result.getInt("id"), result.getString("name"),
					result.getString("surname"), result.getString("email"), result.getString("mobile"),
					result.getInt("role_id"), result.getString("password_hash"));
		}
		dispose();	
		return null;
	}
	
	public List<Room> getRoomsInDateRangeAndCapacity(Date from, Date to, int capacity) throws SQLException {
		connect();
		List<Room> rooms = new ArrayList<Room>();
		
		// Get all bookings in given date range (sub-query)
		String bookingsSql = "(SELECT DISTINCT room_id FROM bookings WHERE (check_in<? AND check_out>=? AND check_out <=?) OR "
				+ "(check_in>=? AND check_out<=?) OR"
				+ "(check_in>=? AND check_in<=? AND check_out>?) OR"
				+ "(check_in<? AND check_out>?) "
				+ "AND capacity=?)";
		String sql = "SELECT * FROM rooms WHERE status_id=1 AND id NOT IN " + bookingsSql;
		stmt = conn.prepareStatement(sql);
		
		stmt.setDate(1, new java.sql.Date(from.getTime()));
		stmt.setDate(2, new java.sql.Date(from.getTime()));
		stmt.setDate(3, new java.sql.Date(to.getTime()));
		
		stmt.setDate(4, new java.sql.Date(from.getTime()));
		stmt.setDate(5, new java.sql.Date(to.getTime()));
		
		stmt.setDate(6, new java.sql.Date(from.getTime()));
		stmt.setDate(7, new java.sql.Date(to.getTime()));
		stmt.setDate(8, new java.sql.Date(to.getTime()));
		
		stmt.setDate(9, new java.sql.Date(from.getTime()));
		stmt.setDate(10, new java.sql.Date(to.getTime()));
		
		stmt.setInt(11, capacity);
		
		// Get all available rooms
		ResultSet result = stmt.executeQuery();
		
		while (result.next()) {
			int id = result.getInt("id");
			capacity = result.getInt("capacity");
			double price = result.getDouble("price");
			String description = result.getString("description");
			String image_encoded = result.getString("image_encoded");
			int status_id = result.getInt("status_id");
			Room room = new Room(id, capacity, price, description, image_encoded, status_id);
			rooms.add(room);
		} 
		dispose();		
		return rooms;
	}
	
	public boolean bookRoom(int id, int user_id, int persons, double total_price, Date check_in, Date check_out) throws SQLException {
		try {
			conn.setAutoCommit(false);
			
			// Insert and set the booking's status to pending
			String insertBookingSql = "INSERT INTO bookings (room_id, user_id, persons, total_price, check_in, check_out, status_id) VALUES "
					+ "(?, ?, ?, ?, ?, 1)"; 
			
			stmt = conn.prepareStatement(insertBookingSql);
			stmt.setInt(1, id);
			stmt.setInt(2, user_id);
			stmt.setInt(3, persons);
			stmt.setDouble(4, total_price);
			stmt.setDate(5, (java.sql.Date) check_in);
			stmt.setDate(6, (java.sql.Date) check_out);		
			stmt.executeQuery();
			
			// Set the room's status to unavailable
			String updateRoomStatusSql = "UPDATE rooms SET status_id=2 WHERE id=?";
			stmt = conn.prepareStatement(updateRoomStatusSql);
			stmt.setInt(1, id);
			stmt.executeQuery();
			conn.commit();
			return true;
		} catch (SQLException ex) {
			return false;
		} finally {
			dispose();			
		}		
	}
	
	private void connect() throws SQLException {
		conn = DriverManager.getConnection(DB_URL,USER,PASS);	
	}
	
	private void dispose() throws SQLException {
		if (!stmt.isClosed())
			stmt.close();
		if (!conn.isClosed())
			conn.close();
	}
}

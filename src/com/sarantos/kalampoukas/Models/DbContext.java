package com.sarantos.kalampoukas.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.sarantos.kalampoukas.UserSession;

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
	
	public void registerCustomer(User user) throws SQLException {
		connect();
		String sql = "INSERT INTO users (email, name, surname, mobile, password_hash, role_id) VALUES (?, ?, ?, ?, ?, ?)";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, user.getEmail());
		stmt.setString(2, user.getName());
		stmt.setString(3, user.getSurname());
		stmt.setString(4, user.getMobile());
		stmt.setString(5, user.getPasswordHash());
		stmt.setInt(6, 2);
		stmt.executeUpdate();
			
		dispose();	
	}
	
	public User findUserByEmailAndRole(String email, String role) throws SQLException {
		connect();
		String sql = "SELECT * FROM users JOIN roles ON users.role_id=roles.id WHERE users.email=? AND roles.name=?";
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, email);
		stmt.setString(2, role);
		ResultSet result = stmt.executeQuery();
			
		if (result.next()) {
			return new User(result.getInt("id"), result.getString("name"),
					result.getString("surname"), result.getString("email"), result.getString("mobile"),
					result.getInt("role_id"), result.getString("password_hash"));
		}
		dispose();	
		return null;
	}
	
	public List<Booking> getBookings() throws SQLException {
		connect();
		
		List<Booking> bookings = new ArrayList<Booking>();
		
		String sql = "SELECT bookings.*, users.email AS email FROM bookings JOIN users ON bookings.user_id=users.id ORDER BY bookings.check_in DESC";
		
		stmt = conn.prepareStatement(sql);
		
		ResultSet result = stmt.executeQuery();
		
		while (result.next()) {
			int id = result.getInt("id");
			int user_id = result.getInt("user_id");
			int persons = result.getInt("persons");
			double total_price = result.getDouble("total_price");
			Date check_in = result.getDate("check_in");
			Date check_out = result.getDate("check_out");
			int status_id = result.getInt("status_id");
			int room_id = result.getInt("room_id");
			String email = result.getString("email");
			Booking booking = new Booking(id, user_id, persons, total_price, check_in, check_out, status_id, room_id, email);
			bookings.add(booking);
		} 
		dispose();		
		return bookings;
	}
	
	public List<Booking> getBookings(int userId) throws SQLException {
		connect();
		
		List<Booking> bookings = new ArrayList<Booking>();
		
		String sql = "SELECT bookings.*, users.email AS email FROM bookings JOIN users ON bookings.user_id=users.id WHERE "
				+ "users.id=? ORDER BY bookings.check_in DESC";
		
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, userId);
		
		ResultSet result = stmt.executeQuery();
		
		while (result.next()) {
			int id = result.getInt("id");
			int user_id = result.getInt("user_id");
			int persons = result.getInt("persons");
			double total_price = result.getDouble("total_price");
			Date check_in = result.getDate("check_in");
			Date check_out = result.getDate("check_out");
			int status_id = result.getInt("status_id");
			int room_id = result.getInt("room_id");
			String email = result.getString("email");
			Booking booking = new Booking(id, user_id, persons, total_price, check_in, check_out, status_id, room_id, email);
			bookings.add(booking);
		} 
		dispose();		
		return bookings;
	}
	
	public boolean setRoomPrice(int id, double price) throws SQLException {		
		try {
			connect();			
			String sql = "UPDATE rooms SET price=? WHERE id=?";			
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, price);
			stmt.setInt(2, id);			
			stmt.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			dispose();
		}
	}
	
	public List<Role> getRoles() throws SQLException {
		connect();
		
		List<Role> roles = new ArrayList<Role>();
		
		String sql = "SELECT * FROM roles";
		
		stmt = conn.prepareStatement(sql);
		
		ResultSet result = stmt.executeQuery();
		
		while (result.next()) {
			int id = result.getInt("id");
			String name = result.getString("name");
			Role role = new Role(id, name);
			roles.add(role);
		} 
		dispose();		
		return roles;
	}
	
	public List<Room> getRoomsInDateRangeAndCapacity(Date from, Date to, int capacity) throws SQLException {
		connect();
		List<Room> rooms = new ArrayList<Room>();
		
		// Get all bookings in given date range (sub-query)
		String bookingsSql = "(SELECT DISTINCT room_id FROM bookings WHERE ((check_in<? AND check_out>=? AND check_out <=?) OR "
				+ "(check_in>=? AND check_out<=?) OR"
				+ "(check_in>=? AND check_in<=? AND check_out>?) OR"
				+ "(check_in<? AND check_out>?)) "
				+ "AND capacity=? AND status_id<>3)";
		String sql = "SELECT * FROM rooms WHERE status_id=1 AND id NOT IN " + bookingsSql + " AND capacity=?";
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
		stmt.setInt(12, capacity);
		
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
	
	public boolean payBooking(long id) throws SQLException {
		try {
			connect();
			
			// Update booking's status to to successful
			String updateBookingSql = "UPDATE bookings SET status_id=? WHERE id=?";
			
			stmt = conn.prepareStatement(updateBookingSql);
			stmt.setInt(1, 2);
			stmt.setLong(2, id);		
			stmt.executeUpdate();			
			
			return true;
		} catch (SQLException ex) {
			System.out.println(ex.toString());
			return false;
		} finally {
			dispose();			
		}		
	}
	
	public boolean activateRoom(int id) throws SQLException {
		try {
			connect();
			
			String sql = "UPDATE rooms SET status_id=1 WHERE id=?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);		
			stmt.executeUpdate();			
			return true;
		} catch (SQLException ex) {
			System.out.println(ex.toString());
			return false;
		} finally {
			dispose();			
		}	
	}
	
	public boolean deactivateRoom(int id) throws SQLException {
		try {
			connect();
			
			String sql = "UPDATE rooms SET status_id=2 WHERE id=?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);		
			stmt.executeUpdate();			
			return true;
		} catch (SQLException ex) {
			System.out.println(ex.toString());
			return false;
		} finally {
			dispose();			
		}	
	}
	
	public boolean cancelBooking(long id) throws SQLException {
		try {
			connect();
			
			// Update booking's status to to successful
			String updateBookingSql = "UPDATE bookings SET status_id=? WHERE id=?";
			
			stmt = conn.prepareStatement(updateBookingSql);
			stmt.setInt(1, 3);
			stmt.setLong(2, id);		
			stmt.executeUpdate();			
			
			return true;
		} catch (SQLException ex) {
			System.out.println(ex.toString());
			return false;
		} finally {
			dispose();			
		}		
	}
	
	public long bookRoom(int id, int user_id, int persons, double total_price, Date check_in, Date check_out) throws SQLException {
		try {
			connect();
			
			// Insert and set the booking's status to pending
			String insertBookingSql = "INSERT INTO bookings (room_id, user_id, persons, total_price, check_in, check_out, status_id) VALUES "
					+ "(?, ?, ?, ?, ?, ?, 1)"; 
			
			stmt = conn.prepareStatement(insertBookingSql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, id);
			stmt.setInt(2, user_id);
			stmt.setInt(3, persons);
			stmt.setDouble(4, total_price);
			stmt.setDate(5, new java.sql.Date(check_in.getTime()));
			stmt.setDate(6, new java.sql.Date(check_out.getTime()));		

			int affectedRows = stmt.executeUpdate();
	        if (affectedRows == 0) {
	            return affectedRows;
	        }

	        ResultSet generatedKeys = stmt.getGeneratedKeys();
	        
	        long bookingId = 0;
            if (generatedKeys.next()) {
                bookingId = generatedKeys.getLong(1);
            }	 
            
            return bookingId;
		} catch (SQLException ex) {
			System.out.println(ex.toString());
			return 0;
		} finally {
			dispose();			
		}		
	}
	
	private void connect() throws SQLException {
		conn = DriverManager.getConnection(DB_URL,USER,PASS);	
	}
	
	private void dispose() throws SQLException {
		if (stmt != null && !stmt.isClosed())
			stmt.close();
		if (conn != null && !conn.isClosed())
			conn.close();
	}

	public List<Room> getRooms() throws SQLException {
		try {
			connect();
			List<Room> rooms = new ArrayList<Room>();
			
			String sql = "SELECT rooms.*, room_statuses.name as status FROM rooms JOIN room_statuses ON rooms.status_id=room_statuses.id ORDER BY rooms.id";
			stmt = conn.prepareStatement(sql);
			// Get all available rooms
			ResultSet result;
			result = stmt.executeQuery();
			while (result.next()) {
				int id = result.getInt("id");
				int capacity = result.getInt("capacity");
				double price = result.getDouble("price");
				String description = result.getString("description");
				String image_encoded = result.getString("image_encoded");
				int status_id = result.getInt("status_id");
				String status_name = result.getString("status");
				Room room = new Room(id, capacity, price, description, image_encoded, status_id, new RoomStatus(status_id, status_name));
				rooms.add(room);
			} 
			
			return rooms;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			dispose();
		}		
			
		
	}
}

package com.sarantos.kalampoukas.Controllers;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.sarantos.kalampoukas.UserSession;
import com.sarantos.kalampoukas.Models.DbContext;
import com.sarantos.kalampoukas.Models.Room;

public class RoomController {
	
	public RoomController() {}
	
	public List<Room> getRooms(Date from, Date to, int capacity) throws Exception {
		DbContext context;
		try {
			context = new DbContext();
			List<Room> rooms = context.getRoomsInDateRangeAndCapacity(from, to, capacity);
			UserSession.getInstance().setCheckIn(from);
			UserSession.getInstance().setCheckOut(to);
			UserSession.getInstance().setPersons(capacity);
			return rooms;
		} catch (ClassNotFoundException | SQLException e) {			
			e.printStackTrace();
			throw new Exception();
		}
		
	}
	
	public List<Room> getRooms() throws Exception {
		DbContext context;
		try {
			context = new DbContext();
			List<Room> rooms = context.getRooms();
			return rooms;
		} catch (ClassNotFoundException | SQLException e) {			
			e.printStackTrace();
			throw new Exception();
		}
		
	}
	
	public long book(int id, int capacity, double total_price) throws Exception {
		DbContext context;
		try {
			context = new DbContext();
			int user_id = UserSession.getInstance().getUserId();
			int persons = UserSession.getInstance().getPersons();
			Date check_in = UserSession.getInstance().getCheckIn();
			Date check_out = UserSession.getInstance().getCheckOut();
			
			long bookingId = context.bookRoom(id, user_id, persons, total_price, check_in, check_out);
			UserSession.getInstance().setBookingId(bookingId);
			System.out.println("Booking Id = " + UserSession.getInstance().getBookingId());
			return UserSession.getInstance().getBookingId();
		} catch (ClassNotFoundException | SQLException e) {			
			e.printStackTrace();
			throw new Exception();
		}
		
	}
	
	public boolean activateRoom(int id) {
		try {
			DbContext context = new DbContext();
			return context.activateRoom(id);
		} catch (ClassNotFoundException | SQLException e) {
			return false;	
		}		
			
	}
	
	public boolean deactivateRoom(int id) {
		try {
			DbContext context = new DbContext();
			return context.deactivateRoom(id);
		} catch (ClassNotFoundException | SQLException e) {
			return false;		
		}		
	}
	
	public boolean setRoomPrice(int id, double price) {
		try {
			DbContext context = new DbContext();
			return context.setRoomPrice(id, price);
		} catch (ClassNotFoundException | SQLException e) {
			return false;		
		}	
	}
}
